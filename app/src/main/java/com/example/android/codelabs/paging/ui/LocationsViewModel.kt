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

package com.example.android.codelabs.paging.ui

import androidx.lifecycle.*
import com.example.android.codelabs.paging.data.LocationsRepository
import com.example.android.codelabs.paging.model.LocatResult
import com.example.android.codelabs.paging.model.location.LocationsParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LocationsViewModel(
    private val repository: LocationsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: LiveData<UiStateLocat>

    val accept: (UiActionLocat) -> Unit

    init {
        val queryLiveData =
            MutableLiveData(savedStateHandle.get(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY)

        state = queryLiveData
            .distinctUntilChanged()
            .switchMap { queryString ->
                liveData {
                    val locParams = LocationsParams(
                        name = queryString,
                        type = "",
                        dimension = "",
                        page = 1,
                    )
                    val uiStateLocat = repository.getSearchResultStream(
                        locParams
                    )
                        .map {
                            UiStateLocat(
                                query = queryString,
                                searchResult = it
                            )
                        }
                        .asLiveData(Dispatchers.Main)
                    emitSource(uiStateLocat)
                }
            }

        accept = { action ->
            when (action) {
                is UiActionLocat.Search -> queryLiveData.postValue(action.query)
                is UiActionLocat.Scroll -> if (action.shouldFetchMore) {
                    val immutableQuery = queryLiveData.value
                    if (immutableQuery != null) {
                        val locParams = LocationsParams(
                            name = immutableQuery,
                            type = "",
                            dimension = "",
                            page = 1,
                        )
                        viewModelScope.launch {
                            repository.requestMore(
                                locParams
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value?.query
        super.onCleared()
    }
}

private val UiActionLocat.Scroll.shouldFetchMore
    get() = visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount

sealed class UiActionLocat {
    data class Search(val query: String) : UiActionLocat()
    data class Scroll(
        val visibleItemCount: Int,
        val lastVisibleItemPosition: Int,
        val totalItemCount: Int
    ) : UiActionLocat()
}

data class UiStateLocat(
    val query: String,
    val searchResult: LocatResult
)

private const val VISIBLE_THRESHOLD = 5
private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val DEFAULT_QUERY = ""