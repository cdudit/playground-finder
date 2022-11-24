package fr.cdudit.playgroundfinder.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.cdudit.playgroundfinder.api.repositories.playground.PlaygroundRepository
import fr.cdudit.playgroundfinder.models.PlaygroundApi
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class HomeViewModel(private val playgroundRepository: PlaygroundRepository) : ViewModel() {
    fun getPlaygrounds(onSuccess: (PlaygroundApi?) -> Unit, onError: (ResponseBody?) -> Unit) {
        viewModelScope.launch {
            val response = playgroundRepository.getPlaygroundList()
            if (response.isSuccessful) {
                onSuccess(response.body())
            } else {
                onError(response.errorBody())
            }
        }
    }
}