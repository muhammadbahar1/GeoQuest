package ac.uk.kingston.k2323158.geoquest.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import android.annotation.SuppressLint
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import ac.uk.kingston.k2323158.geoquest.ui.components.BottomNavBar
import ac.uk.kingston.k2323158.geoquest.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL

data class CacheItem(
    val cacheId: Int,
    val cacheName: String,
    val latitude: Double,
    val longitude: Double,
    val cachePoints: Int,
    val cacheFound: Boolean
)

suspend fun fetchCaches(): List<CacheItem> {
    return withContext(Dispatchers.IO) {
        try {
            val url = "http://ec2-13-134-244-170.eu-west-2.compute.amazonaws.com/v1/active_caches"
            val response = URL(url).readText()
            val jsonArray = JSONArray(response)
            val caches = mutableListOf<CacheItem>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                caches.add(
                    CacheItem(
                        cacheId = obj.getInt("cacheid"),
                        cacheName = obj.getString("cachename"),
                        latitude = obj.getDouble("cachelatitude"),
                        longitude = obj.getDouble("cachelongitude"),
                        cachePoints = obj.getInt("cachepoints"),
                        cacheFound = obj.getBoolean("cachefoundbool")
                    )
                )
            }
            caches
        } catch (e: Exception) {
            emptyList()
        }
    }
}

@Composable
fun GlobalModeScreen() {
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
                0 -> MapTabContent()
                1 -> NotificationsTabContent()
                2 -> LeaderboardTabContent()
                3 -> ProfileTabContent()
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun MapTabContent() {
    val context = LocalContext.current
    var caches by remember { mutableStateOf<List<CacheItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
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

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(51.5, -0.1), 14f)
    }

    // Continuously follow user location
    LaunchedEffect(hasLocationPermission) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                2000L // update every 2 seconds
            ).build()

            val locationCallback = object : com.google.android.gms.location.LocationCallback() {
                override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                    result.lastLocation?.let { location ->
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LatLng(location.latitude, location.longitude),
                            14f
                        )
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                android.os.Looper.getMainLooper()
            )
        }
        caches = fetchCaches()
        isLoading = false
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
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission)
        ) {
            caches.forEach { cache ->
                Marker(
                    state = MarkerState(
                        position = LatLng(cache.latitude, cache.longitude)
                    ),
                    title = cache.cacheName,
                    snippet = "${cache.cachePoints} points"
                )
            }
        }
    }
}