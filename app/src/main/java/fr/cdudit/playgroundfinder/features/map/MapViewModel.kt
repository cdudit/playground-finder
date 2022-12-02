package fr.cdudit.playgroundfinder.features.map

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import fr.cdudit.playgroundfinder.R
import fr.cdudit.playgroundfinder.api.repositories.playground.PlaygroundRepository
import fr.cdudit.playgroundfinder.managers.PreferencesManager
import fr.cdudit.playgroundfinder.models.PlaygroundApi
import fr.cdudit.playgroundfinder.models.Record
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class MapViewModel (private val playgroundRepository: PlaygroundRepository) : ViewModel() {
    fun getPlaygrounds(onSuccess: (List<Record>?) -> Unit, onError: (ResponseBody?) -> Unit) {
        viewModelScope.launch {
            val response = playgroundRepository.getPlaygroundList(null, null,null)
            if (response.isSuccessful) {
                onSuccess(response.body()?.records)
            } else {
                onError(response.errorBody())
            }
        }
    }

    fun setFavorites(context: Context, favorites: ArrayList<String>) {
        PreferencesManager.setFavorites(context, favorites)
    }

    fun getPinIcon(record: Record): BitmapDescriptor {
        val iconId = if (record.isFavorite) R.drawable.ic_pin_favorite else R.drawable.ic_pin
        return BitmapDescriptorFactory.fromResource(iconId)
    }

    fun mapWithFavorites(context: Context, records: List<Record>): List<Record> {
        val favoritesId = PreferencesManager.getFavorites(context)
        return records.map { record ->
            record.isFavorite = favoritesId.any { record.recordId == it }
            return@map record
        }
    }
}