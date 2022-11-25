package fr.cdudit.playgroundfinder.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.cdudit.playgroundfinder.api.repositories.playground.PlaygroundRepository
import fr.cdudit.playgroundfinder.models.PlaygroundApi
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class PlaygroundListViewModel(private val playgroundRepository: PlaygroundRepository) : ViewModel() {
    fun getPlaygrounds(
        ageMin: Int?,
        ageMax: Int?,
        search: String?,
        onSuccess: (PlaygroundApi?) -> Unit,
        onError: (ResponseBody?) -> Unit
    ) {
        viewModelScope.launch {
            val response = playgroundRepository.getPlaygroundList(ageMin, ageMax, search)
            if (response.isSuccessful) {
                onSuccess(response.body())
            } else {
                onError(response.errorBody())
            }
        }
    }
}