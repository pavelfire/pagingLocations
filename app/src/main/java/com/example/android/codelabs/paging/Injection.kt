package com.example.android.codelabs.paging

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.android.codelabs.paging.api.RickAndMortyService
import com.example.android.codelabs.paging.data.LocationsRepository
import com.example.android.codelabs.paging.ui.LocationViewModelFactory

object Injection {

    private fun provideLocationsRepository(): LocationsRepository {
        return LocationsRepository(RickAndMortyService.create())
    }

    fun provideLocationViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return LocationViewModelFactory(owner, provideLocationsRepository())
    }
}
