package ac.uk.kingston.k2323158.geoquest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL

data class LeaderboardEntry(val username: String, val score: Int)

class LeaderboardViewModel : ViewModel() {

    private val _entries = MutableStateFlow<List<LeaderboardEntry>>(emptyList())
    val entries: StateFlow<List<LeaderboardEntry>> = _entries

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val baseUrl = "http://ec2-13-134-244-170.eu-west-2.compute.amazonaws.com"

    init {
        fetchLeaderboard()
    }

    fun fetchLeaderboard() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = withContext(Dispatchers.IO) {
                    val url = URL("$baseUrl/v1/users")
                    val response = url.readText()
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
                    users.sortedByDescending { it.score }
                }
                _entries.value = result
            } catch (e: Exception) {
                android.util.Log.e("LeaderboardViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}