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

package com.example.android.codelabs.paging.data

import android.util.Log
import com.example.android.codelabs.paging.api.RickAndMortyService
import com.example.android.codelabs.paging.model.LocatResult
import com.example.android.codelabs.paging.model.location.LocationDTO
import com.example.android.codelabs.paging.model.location.LocationsParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val STARTING_PAGE_INDEX = 1

class LocationsRepository(private val service: RickAndMortyService) {

    private val inMemoryCache = mutableListOf<LocationDTO>()
    private val searchResults = MutableSharedFlow<LocatResult>(replay = 1)
    private var lastRequestedPage = STARTING_PAGE_INDEX
    private var isRequestInProgress = false
    private var pages = -1

    suspend fun getSearchResultStream(query: LocationsParams): Flow<LocatResult> {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = STARTING_PAGE_INDEX
        inMemoryCache.clear()
        requestAndSaveData(query)

        return searchResults
    }

    suspend fun requestMore(query: LocationsParams) {
        if (isRequestInProgress) return
        val successful = requestAndSaveData(query)
        if (successful) {
            lastRequestedPage++
        }
    }

    suspend fun retry(query: LocationsParams) {
        if (isRequestInProgress) return
        requestAndSaveData(query)
    }

    private suspend fun requestAndSaveData(params: LocationsParams): Boolean {
        isRequestInProgress = true
        var successful = false

        if (lastRequestedPage == pages + 1) return false

        try {
            val response = service.searchLocations(
                name = params.name,
                type = params.type,
                dimension = params.dimension,
                page = lastRequestedPage,
            )
            Log.d("LocationsRepository", "---------response $response")
            Log.d("LocationsRepository", "=========lastRequestedPage: $lastRequestedPage")
            pages = response.info.pages
            val repos = response.results ?: emptyList()
            inMemoryCache.addAll(repos)
            val reposByName = reposByName(params.name)
            searchResults.emit(LocatResult.Success(reposByName))
            successful = true
        } catch (exception: IOException) {
            searchResults.emit(LocatResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.emit(LocatResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }

    private fun reposByName(query: String): List<LocationDTO> {

        return inMemoryCache.filter {
            it.name.contains(query, true) ||
                    (it.name != null && it.name.contains(query, true))
        }
        //.sortedWith(compareByDescending<LocationDTO> { it.id }.thenBy { it.name })
    }
}
