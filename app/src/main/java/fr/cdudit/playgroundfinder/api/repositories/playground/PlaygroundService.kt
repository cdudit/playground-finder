package fr.cdudit.playgroundfinder.api.repositories.playground

import fr.cdudit.playgroundfinder.models.PlaygroundApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaygroundService {
    @GET("search/?dataset=bor_airejeux&rows=20")
    suspend fun getPlaygroundList(
        @Query("refine.age_min") ageMin: Int?,
        @Query("refine.age_max") ageMax: Int?,
        @Query("q") search: String?,
    ): Response<PlaygroundApi>
}