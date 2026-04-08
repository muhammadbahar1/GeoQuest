package ac.uk.kingston.k2323158.geoquest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ac.uk.kingston.k2323158.geoquest.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL

data class LeaderboardEntry(val username: String, val score: Int)

suspend fun fetchLeaderboard(): List<LeaderboardEntry> {
    return withContext(Dispatchers.IO) {
        try {
            val url = "http://ec2-13-134-244-170.eu-west-2.compute.amazonaws.com/v1/users"
            val response = URL(url).readText()
            val jsonArray = JSONArray(response)
            val users = mutableListOf<LeaderboardEntry>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                users.add(
                    LeaderboardEntry(
                        username = obj.getString("username"),
                        score = obj.getInt("userpointsglobal")
                    )
                )
            }
            // Sort by highest score first
            users.sortedByDescending { it.score }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

@Composable
fun LeaderboardTabContent() {
    var entries by remember { mutableStateOf<List<LeaderboardEntry>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        entries = fetchLeaderboard()
        isLoading = false
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
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

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = DarkForestGreen)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading leaderboard...",
                        color = DarkForestGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
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
                            text = when (index) {
                                0 -> "🥇"
                                1 -> "🥈"
                                2 -> "🥉"
                                else -> "#${index + 1}"
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkForestGreen,
                            modifier = Modifier.width(40.dp)
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
}