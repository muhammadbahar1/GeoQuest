package ac.uk.kingston.k2323158.geoquest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ac.uk.kingston.k2323158.geoquest.ui.theme.*
import ac.uk.kingston.k2323158.geoquest.viewmodel.MapViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NotificationsTabContent(mapViewModel: MapViewModel) {
    val alerts by mapViewModel.alerts.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkForestGreen)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Alerts",
                color = WhiteText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (alerts.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = DarkForestGreen,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No Alerts Yet",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkForestGreen
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "You'll be notified when you're near a cache!",
                    fontSize = 14.sp,
                    color = DarkGrayText
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(alerts) { alert ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = DarkForestGreen.copy(alpha = 0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🎉", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = alert,
                                fontSize = 14.sp,
                                color = DarkGrayText,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}