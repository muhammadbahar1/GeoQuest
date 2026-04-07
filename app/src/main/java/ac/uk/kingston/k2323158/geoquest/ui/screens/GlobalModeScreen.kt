package ac.uk.kingston.k2323158.geoquest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ac.uk.kingston.k2323158.geoquest.ui.components.BottomNavBar
import ac.uk.kingston.k2323158.geoquest.ui.theme.*


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

@Composable
fun MapTabContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Default.Map,
            contentDescription = null,
            tint = DarkForestGreen,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Map Loading...",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DarkForestGreen
        )
    }
}
