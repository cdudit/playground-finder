package fr.cdudit.playgroundfinder.api.repositories.playground

import fr.cdudit.playgroundfinder.models.PlaygroundApi
import retrofit2.Response
import retrofit2.http.GET

interface PlaygroundService {
    @GET("search/?dataset=bor_airejeux")
    suspend fun getPlaygroundList(): Response<PlaygroundApi>
}