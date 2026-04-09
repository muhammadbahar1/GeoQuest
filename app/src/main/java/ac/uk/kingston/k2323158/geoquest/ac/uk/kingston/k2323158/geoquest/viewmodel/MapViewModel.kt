package ac.uk.kingston.k2323158.geoquest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class CacheItem(
    val cacheId: Int,
    val cacheName: String,
    val latitude: Double,
    val longitude: Double,
    val cachePoints: Int,
    val cacheFound: Boolean
)

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val _caches = MutableStateFlow<List<CacheItem>>(emptyList())
    val caches: StateFlow<List<CacheItem>> = _caches

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _claimedCaches = MutableStateFlow<Set<Int>>(emptySet())
    val claimedCaches: StateFlow<Set<Int>> = _claimedCaches

    private val _userScore = MutableStateFlow(0)
    val userScore: StateFlow<Int> = _userScore

    private val _alerts = MutableStateFlow<List<String>>(emptyList())
    val alerts: StateFlow<List<String>> = _alerts

    private val baseUrl = "http://ec2-13-134-244-170.eu-west-2.compute.amazonaws.com"

    init {
        fetchCaches()
    }

    fun fetchCaches() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = withContext(Dispatchers.IO) {
                    val url = URL("$baseUrl/v1/active_caches")
                    val response = url.readText()
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
                }
                _caches.value = result
            } catch (e: Exception) {
                android.util.Log.e("MapViewModel", "Error fetching caches: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onCacheFound(username: String, cache: CacheItem) {
        if (_claimedCaches.value.contains(cache.cacheId)) return

        _claimedCaches.value = _claimedCaches.value + cache.cacheId
        _userScore.value += cache.cachePoints

        val message = "Cache found: ${cache.cacheName} +${cache.cachePoints} pts! Total: ${_userScore.value} pts"
        _alerts.value = listOf(message) + _alerts.value

        postCacheFound(username, cache.cacheId)
    }

    private fun postCacheFound(username: String, cacheId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val url = URL("$baseUrl/v1/users")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.doOutput = true

                    val body = JSONObject().apply {
                        put("username", username)
                        put("cachefound", cacheId)
                    }.toString()

                    connection.outputStream.write(body.toByteArray())
                    android.util.Log.d("MapViewModel", "POST response: ${connection.responseCode}")
                } catch (e: Exception) {
                    android.util.Log.e("MapViewModel", "Error posting cache: ${e.message}")
                }
            }
        }
    }
}