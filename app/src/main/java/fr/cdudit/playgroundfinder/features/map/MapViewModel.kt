package fr.cdudit.playgroundfinder.features.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.cdudit.playgroundfinder.api.repositories.playground.PlaygroundRepository
import fr.cdudit.playgroundfinder.models.PlaygroundApi
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class MapViewModel (private val playgroundRepository: PlaygroundRepository) : ViewModel() {
    fun getPlaygrounds(onSuccess: (PlaygroundApi?) -> Unit, onError: (ResponseBody?) -> Unit) {
        viewModelScope.launch {
            val response = playgroundRepository.getPlaygroundList(null, null,null)
            if (response.isSuccessful) {
                onSuccess(response.body())
            } else {
                onError(response.errorBody())
            }
        }
    }
}