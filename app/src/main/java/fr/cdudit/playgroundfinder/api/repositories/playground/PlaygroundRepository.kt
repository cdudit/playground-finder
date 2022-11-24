package fr.cdudit.playgroundfinder.api.repositories.playground

import fr.cdudit.playgroundfinder.api.ApiManager
import fr.cdudit.playgroundfinder.models.PlaygroundApi
import retrofit2.Response

class PlaygroundRepository : ApiManager(), PlaygroundService {
    private val service = retrofit.create(PlaygroundService::class.java)

    override suspend fun getPlaygroundList(): Response<PlaygroundApi> {
        return service.getPlaygroundList()
    }
}