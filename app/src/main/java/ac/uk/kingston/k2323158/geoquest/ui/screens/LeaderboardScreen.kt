package ac.uk.kingston.k2323158.geoquest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ac.uk.kingston.k2323158.geoquest.ui.theme.*

data class LeaderboardEntry(val username: String, val score: Int)

val dummyLeaderboard = listOf(
    LeaderboardEntry("MasDeabes", 4521),
    LeaderboardEntry("SifQuest", 4200),
    LeaderboardEntry("GeoHunter99", 3900),
    LeaderboardEntry("CacheKing", 3500),
    LeaderboardEntry("TreasureSeeker", 3100),
)

@Composable
fun LeaderboardTabContent() {
    //Replace Hardcoded with API call when backend is ready
    val entries = dummyLeaderboard

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkForestGreen)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Leaderboard",
                color = WhiteText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(entries) { index, entry ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${index + 1}.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkForestGreen,
                        modifier = Modifier.width(32.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = DarkForestGreen
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = entry.username,
                        fontSize = 16.sp,
                        color = DarkGrayText,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${entry.score} pts",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkForestGreen
                    )
                }
                HorizontalDivider(color = DarkGrayText.copy(alpha = 0.2f))
            }
        }
    }
}