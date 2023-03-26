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

package com.example.android.codelabs.paging.ui.location

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.android.codelabs.paging.data.LocationsRepository
import com.example.android.codelabs.paging.model.location.LocationEntity
import com.example.android.codelabs.paging.ui.repo.UiAction
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"
private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val VISIBLE_THRESHOLD = 5
private const val DEFAULT_QUERY = ""

class LocationsViewModel(
    private val repository: LocationsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: StateFlow<UiStateLocat>
    val pagingDataFlow: Flow<PagingData<UiModelLocat>>
    val accept: (UiActionLocat) -> Unit

//    private val UiModelLocat.LocatItem.roundedStarCount: Int
//        get() = this.locat.id / 5

    init {
        val initialQuery: String = savedStateHandle.get(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        val lastQueryScrolled: String = savedStateHandle.get(LAST_QUERY_SCROLLED) ?: DEFAULT_QUERY
        val actionStateFlow = MutableSharedFlow<UiActionLocat>()
        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(query = initialQuery)) }
        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            // This is shared to keep the flow "hot" while caching the last query scrolled,
            // otherwise each flatMapLatest invocation would lose the last query scrolled,
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(currentQuery = lastQueryScrolled)) }

        pagingDataFlow = searches
            .flatMapLatest { searchLocations(queryString = it.query) }
            .cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            UiStateLocat(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                // If the search query matches the scroll query, the user has scrolled
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiStateLocat()
            )

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }


    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        savedStateHandle[LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
        super.onCleared()
    }

    private fun searchLocations(queryString: String): Flow<PagingData<UiModelLocat>> =
        repository.getSearchResultStream(queryString)
            .map { pagingData -> pagingData.map { UiModelLocat.LocatItem(it) } }
            /*
            .map {
                it.insertSeparators { before, after ->
                    if (after == null) {
                        // we're at the end of the list
                        return@insertSeparators null
                    }

                    if (before == null) {
                        // we're at the beginning of the list
                        return@insertSeparators UiModelLocat.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                    }
                    // check between 2 items
                    if (before.roundedStarCount > after.roundedStarCount) {
                        if (after.roundedStarCount >= 1) {
                            UiModelLocat.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                        } else {
                            UiModelLocat.SeparatorItem("< 10.000+ stars")
                        }
                    } else {
                        // no separator
                        null
                    }
                }


            }

             */
}

sealed class UiActionLocat {
    data class Search(val query: String) : UiActionLocat()
    data class Scroll(val currentQuery: String) : UiActionLocat()
}

data class UiStateLocat(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false
)

sealed class UiModelLocat {
    data class LocatItem(val locat: LocationEntity) : UiModelLocat()
    data class SeparatorItem(val description: String) : UiModelLocat()
}
