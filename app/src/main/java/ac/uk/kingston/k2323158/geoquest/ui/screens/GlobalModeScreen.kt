package ac.uk.kingston.k2323158.geoquest.ui.screens

import android.annotation.SuppressLint
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import ac.uk.kingston.k2323158.geoquest.ui.components.BottomNavBar
import ac.uk.kingston.k2323158.geoquest.ui.theme.*
import ac.uk.kingston.k2323158.geoquest.viewmodel.MapViewModel
import ac.uk.kingston.k2323158.geoquest.viewmodel.LeaderboardViewModel
import ac.uk.kingston.k2323158.geoquest.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun GlobalModeScreen(
    mapViewModel: MapViewModel,
    leaderboardViewModel: LeaderboardViewModel,
    profileViewModel: ProfileViewModel
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LightTanBackground)
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> MapTabContent(
                    mapViewModel = mapViewModel,
                    leaderboardViewModel = leaderboardViewModel,
                    profileViewModel = profileViewModel
                )
                1 -> NotificationsTabContent(mapViewModel = mapViewModel)
                2 -> LeaderboardTabContent(leaderboardViewModel = leaderboardViewModel)
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun MapTabContent(
    mapViewModel: MapViewModel,
    leaderboardViewModel: LeaderboardViewModel,
    profileViewModel: ProfileViewModel
) {
    val context = LocalContext.current
    val caches by mapViewModel.caches.collectAsStateWithLifecycle()
    val isLoading by mapViewModel.isLoading.collectAsStateWithLifecycle()
    val claimedCaches by mapViewModel.claimedCaches.collectAsStateWithLifecycle()
    val userScore by mapViewModel.userScore.collectAsStateWithLifecycle()
    val username by profileViewModel.username.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasLocationPermission = granted
    }

    // Compass sensor
    var bearing by remember { mutableStateOf(0f) }
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    DisposableEffect(Unit) {
        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val rotationMatrix = FloatArray(9)
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                val orientation = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientation)
                bearing = Math.toDegrees(orientation[0].toDouble()).toFloat()
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(
            sensorListener,
            rotationSensor,
            SensorManager.SENSOR_DELAY_UI
        )
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(51.5, -0.1), 14f)
    }

    LaunchedEffect(hasLocationPermission) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                2000L
            ).build()

            val locationCallback = object : com.google.android.gms.location.LocationCallback() {
                override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                    result.lastLocation?.let { location ->
                        cameraPositionState.position = CameraPosition(
                            LatLng(location.latitude, location.longitude),
                            14f,
                            0f,
                            bearing
                        )

                        caches.forEach { cache ->
                            if (!claimedCaches.contains(cache.cacheId)) {
                                val distance = FloatArray(1)
                                android.location.Location.distanceBetween(
                                    location.latitude,
                                    location.longitude,
                                    cache.latitude,
                                    cache.longitude,
                                    distance
                                )

                                if (distance[0] <= 10f) {
                                    mapViewModel.onCacheFound(username, cache) {
                                        leaderboardViewModel.fetchLeaderboard()
                                    }
                                    profileViewModel.updateScore(cache.cachePoints)
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "🎉 Cache found: ${cache.cacheName} +${cache.cachePoints} pts!",
                                            duration = SnackbarDuration.Long
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                android.os.Looper.getMainLooper()
            )
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = DarkForestGreen)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Loading caches...",
                    color = DarkForestGreen,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    } else {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = LightTanBackground
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
                    uiSettings = MapUiSettings(
                        compassEnabled = true,
                        myLocationButtonEnabled = true
                    )
                ) {
                    caches.forEach { cache ->
                        val isClaimed = claimedCaches.contains(cache.cacheId)
                        Marker(
                            state = MarkerState(
                                position = LatLng(cache.latitude, cache.longitude)
                            ),
                            title = if (isClaimed) "✅ ${cache.cacheName}" else cache.cacheName,
                            snippet = if (isClaimed) "Found!" else "${cache.cachePoints} points",
                            alpha = if (isClaimed) 0.5f else 1f
                        )
                    }
                }

                // Score banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkForestGreen.copy(alpha = 0.9f))
                        .padding(8.dp)
                        .align(Alignment.TopCenter)
                ) {
                    Text(
                        text = "🏆 $username  |  Score: $userScore pts",
                        color = WhiteText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}