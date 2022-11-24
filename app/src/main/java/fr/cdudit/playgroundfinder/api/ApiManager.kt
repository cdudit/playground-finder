package fr.cdudit.playgroundfinder.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class ApiManager {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://opendata.bordeaux-metropole.fr/api/records/1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}