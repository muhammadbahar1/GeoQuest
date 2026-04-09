package ac.uk.kingston.k2323158.geoquest.ui.screens

import ac.uk.kingston.k2323158.geoquest.ui.components.GeoQuestButton
import ac.uk.kingston.k2323158.geoquest.ui.theme.DarkForestGreen
import ac.uk.kingston.k2323158.geoquest.ui.theme.DarkGrayText
import ac.uk.kingston.k2323158.geoquest.ui.theme.LightTanBackground
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LandingScreen(onModeSelected: () -> Unit) {
    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(LightTanBackground)
            .padding(24.dp), // Overall padding for the screen
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.Companion.height(64.dp))

        Text(
            text = "GeoQuest",
            color = DarkForestGreen,
            fontSize = 40.sp,
            fontWeight = FontWeight.Companion.ExtraBold
        )

        Spacer(modifier = Modifier.Companion.height(24.dp))


        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "GeoQuest Logo",
            modifier = Modifier.Companion.size(100.dp)
        )

        Spacer(modifier = Modifier.Companion.height(32.dp))

        // 4. Welcome Text
        Text(
            text = "Welcome to GeoQuest!",
            color = DarkGrayText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Companion.Bold
        )

        Spacer(modifier = Modifier.Companion.height(12.dp))

        // 5. Description Text
        Text(
            text = "GeoQuest is a treasure hunt game where you go to real places to find and log virtual treasure using your phone.",
            color = DarkGrayText,
            fontSize = 14.sp,
            textAlign = TextAlign.Companion.Center,
            modifier = Modifier.Companion.padding(horizontal = 16.dp)
        )


        Spacer(modifier = Modifier.Companion.weight(1f))

        GeoQuestButton(
            text = "Play",
            onClick = onModeSelected
        )

        Spacer(modifier = Modifier.Companion.height(16.dp)) // Bottom safe area
    }
}