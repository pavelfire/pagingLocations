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
import com.example.android.codelabs.paging.api.RickAndMortyService
import com.example.android.codelabs.paging.data.db.character.CharactersDatabase
import com.example.android.codelabs.paging.data.db.character.CharactersRepository
import com.example.android.codelabs.paging.data.db.location.LocationsRepository
import com.example.android.codelabs.paging.data.db.location.LocationsDatabase
import com.example.android.codelabs.paging.ui.character.CharacterViewModelFactory
import com.example.android.codelabs.paging.ui.location.LocationViewModelFactory

object Injection {

    private fun provideCharactersRepository(context: Context): CharactersRepository {
        return CharactersRepository(
            RickAndMortyService.create(),
            CharactersDatabase.getInstance(context)
        )
    }

    fun provideCharacterViewModelFactory(
        context: Context,
        owner: SavedStateRegistryOwner
    ): ViewModelProvider.Factory {
        return CharacterViewModelFactory(
            owner,
            provideCharactersRepository(context)
        )
    }

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
