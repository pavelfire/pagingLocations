/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.android.codelabs.paging.api.GithubService
import com.example.android.codelabs.paging.api.RickAndMortyService
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.data.LocationsRepository
import com.example.android.codelabs.paging.data.db.LocationsDatabase
import com.example.android.codelabs.paging.data.db.RepoDatabase
import com.example.android.codelabs.paging.ui.location.LocationViewModelFactory
import com.example.android.codelabs.paging.ui.repo.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(GithubService.create(), RepoDatabase.getInstance(context))
    }

    fun provideViewModelFactory(
        context: Context,
        owner: SavedStateRegistryOwner
    ): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideGithubRepository(context))
    }

    //-----------------------------------------------------------------------------------------

    private fun provideLocationsRepository(context: Context): LocationsRepository {
        return LocationsRepository(
            RickAndMortyService.create(),
            LocationsDatabase.getInstance(context)
        )
    }

    fun provideLocationViewModelFactory(
        context: Context,
        owner: SavedStateRegistryOwner
    ): ViewModelProvider.Factory {
        return LocationViewModelFactory(
            owner,
            provideLocationsRepository(context)
        )
    }
}
