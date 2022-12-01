package fr.cdudit.playgroundfinder.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.cdudit.playgroundfinder.api.repositories.playground.PlaygroundRepository
import fr.cdudit.playgroundfinder.models.PlaygroundApi
import fr.cdudit.playgroundfinder.models.Record
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class PlaygroundListViewModel(private val playgroundRepository: PlaygroundRepository) : ViewModel() {
    fun getPlaygrounds(
        ageMin: Int?,
        ageMax: Int?,
        search: String?,
        onSuccess: (List<Record>?) -> Unit,
        onError: (ResponseBody?) -> Unit
    ) {
        viewModelScope.launch {
            val response = playgroundRepository.getPlaygroundList(ageMin, ageMax, search)
            if (response.isSuccessful) {
                onSuccess(response.body()?.records)
            } else {
                onError(response.errorBody())
            }
        }
    }
}