package fr.cdudit.playgroundfinder

import android.app.Application
import fr.cdudit.playgroundfinder.api.repositories.playground.PlaygroundRepository
import fr.cdudit.playgroundfinder.features.map.MapViewModel
import fr.cdudit.playgroundfinder.features.list.PlaygroundListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    init {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(viewModels, repositories))
        }
    }
}

val viewModels = module {
    viewModel { MapViewModel() }

    viewModel { PlaygroundListViewModel(get()) }
}

val repositories = module {
    single { PlaygroundRepository() }
}