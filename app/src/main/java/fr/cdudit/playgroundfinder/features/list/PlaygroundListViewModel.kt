package fr.cdudit.playgroundfinder.features.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.cdudit.playgroundfinder.api.repositories.playground.PlaygroundRepository
import fr.cdudit.playgroundfinder.managers.PreferencesManager
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

    fun mapWithFavorites(context: Context, records: List<Record>): List<Record> {
        val favoritesId = PreferencesManager.getFavorites(context)
        return records.map { record ->
            record.isFavorite = favoritesId.any { record.recordId == it }
            return@map record
        }
    }
}