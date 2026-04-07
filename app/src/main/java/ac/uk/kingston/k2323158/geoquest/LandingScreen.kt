package ac.uk.kingston.k2323158.geoquest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn // Using a standard icon as a temporary placeholder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ac.uk.kingston.k2323158.geoquest.ui.theme.*
import ac.uk.kingston.k2323158.geoquest.ui.components.GeoQuestButton
import androidx.compose.ui.res.painterResource
import ac.uk.kingston.k2323158.geoquest.R

@Composable
fun LandingScreen(onModeSelected: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightTanBackground)
            .padding(24.dp), // Overall padding for the screen
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "GeoQuest",
            color = DarkForestGreen,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(24.dp))


        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "GeoQuest Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 4. Welcome Text
        Text(
            text = "Welcome to GeoQuest!",
            color = DarkGrayText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 5. Description Text
        Text(
            text = "GeoQuest is a treasure hunt game where you go to real places to find and log virtual treasure using your phone.",
            color = DarkGrayText,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )


        Spacer(modifier = Modifier.weight(1f))

        GeoQuestButton(
            text = "Global Mode",
            onClick = onModeSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        GeoQuestButton(
            text = "Private Event Mode",
            onClick = onModeSelected
        )

        Spacer(modifier = Modifier.height(16.dp)) // Bottom safe area
    }
}