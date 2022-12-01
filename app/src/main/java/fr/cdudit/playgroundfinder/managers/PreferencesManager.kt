package fr.cdudit.playgroundfinder.managers

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.cdudit.playgroundfinder.models.Record
import java.lang.reflect.Type

object PreferencesManager {
    private const val SHARED_PREFERENCES_KEY = "shared_preferences"
    private const val SHARED_PREFERENCES_FAVORITES_KEY = "favorites_id"
    private val gson = Gson()
    private val favoritesIdType: Type = object : TypeToken<ArrayList<String?>?>() {}.type

    fun getFavorites(context: Context): ArrayList<String> {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val favoritesJson = sharedPreferences.getString(SHARED_PREFERENCES_FAVORITES_KEY, null)
        return gson.fromJson(favoritesJson, favoritesIdType) ?: arrayListOf()
    }

    fun setFavorites(context: Context, favoritesId: ArrayList<String>) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString(SHARED_PREFERENCES_FAVORITES_KEY, gson.toJson(favoritesId))
            apply()
        }
    }
}