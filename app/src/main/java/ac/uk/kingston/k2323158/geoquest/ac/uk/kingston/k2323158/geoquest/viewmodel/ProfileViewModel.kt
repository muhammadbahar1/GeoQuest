package ac.uk.kingston.k2323158.geoquest.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _cachesFound = MutableStateFlow(0)
    val cachesFound: StateFlow<Int> = _cachesFound

    fun setUsername(name: String) {
        _username.value = name
    }

    fun updateScore(points: Int) {
        _score.value += points
        _cachesFound.value += 1
    }

    fun reset() {
        _username.value = ""
        _score.value = 0
        _cachesFound.value = 0
    }
}