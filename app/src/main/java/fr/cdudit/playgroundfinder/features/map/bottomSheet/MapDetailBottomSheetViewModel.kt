package fr.cdudit.playgroundfinder.features.map.bottomSheet

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import fr.cdudit.playgroundfinder.R

class MapDetailBottomSheetViewModel : ViewModel() {
    @StringRes
    fun getAgeResId() = R.string.detail_age

    @StringRes
    fun getGamesResId() = R.string.detail_games

    @StringRes
    fun getSurfaceResId() = R.string.detail_surface
}