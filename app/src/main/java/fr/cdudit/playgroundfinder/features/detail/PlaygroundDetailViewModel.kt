package fr.cdudit.playgroundfinder.features.detail

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import fr.cdudit.playgroundfinder.R
import fr.cdudit.playgroundfinder.managers.PreferencesManager
import fr.cdudit.playgroundfinder.models.Record

class PlaygroundDetailViewModel : ViewModel() {
    @StringRes
    fun getAgeResId() = R.string.detail_age

    @StringRes
    fun getGamesResId() = R.string.detail_games

    @StringRes
    fun getSurfaceResId() = R.string.detail_surface

    fun toggleFavorite(context: Context, record: Record) {
        val ids = PreferencesManager.getFavorites(context)
        if (record.isFavorite) {
            if (!ids.contains(record.recordId)) {
                ids.add(record.recordId)
            }
        } else {
            ids.removeIf { it == record.recordId }
        }
        PreferencesManager.setFavorites(context, ids)
    }
}