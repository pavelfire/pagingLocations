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

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.Injection
import com.example.android.codelabs.paging.databinding.ActivityLocationsBinding
import com.example.android.codelabs.paging.ui.repo.ReposLoadStateAdapter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LocationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLocationsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope

        // get the view model
        val viewModel = ViewModelProvider(
            this, Injection.provideLocationViewModelFactory(
                context = this,
                owner = this
            )
        )
            .get(LocationsViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        binding.list.layoutManager = GridLayoutManager(applicationContext, 2)

        // bind the state
        binding.bindStateLocat(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
    }

    private fun ActivityLocationsBinding.bindStateLocat(
        uiState: StateFlow<UiStateLocat>,
        pagingData: Flow<PagingData<UiModelLocat>>,
        uiActions: (UiActionLocat) -> Unit
    ) {
        val repoAdapter = LocationsAdapter()
        val header = ReposLoadStateAdapter { repoAdapter.retry() }
        list.adapter = repoAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = ReposLoadStateAdapter { repoAdapter.retry() }
        )

        bindSearchLocat(
            uiState = uiState,
            onQueryChanged = uiActions
        )
        bindListLocat(
            header = header,
            repoAdapter = repoAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun ActivityLocationsBinding.bindSearchLocat(
        uiState: StateFlow<UiStateLocat>,
        onQueryChanged: (UiActionLocat.Search) -> Unit
    ) {
        searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInputLocat(onQueryChanged)
                true
            } else {
                false
            }
        }
        searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInputLocat(onQueryChanged)
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            uiState
                .map { it.query }
                .distinctUntilChanged()
                .collect(searchRepo::setText)
        }
    }

    private fun ActivityLocationsBinding.updateRepoListFromInputLocat(
        onQueryChanged: (UiActionLocat.Search) -> Unit
    ) {
        searchRepo.text.trim().let {
            //if (it.isNotEmpty()) {
            list.scrollToPosition(0)
            onQueryChanged(UiActionLocat.Search(query = it.toString()))
            //}
        }
    }

    private fun ActivityLocationsBinding.bindListLocat(
        header: ReposLoadStateAdapter,
        repoAdapter: LocationsAdapter,
        uiState: StateFlow<UiStateLocat>,
        pagingData: Flow<PagingData<UiModelLocat>>,
        onScrollChanged: (UiActionLocat.Scroll) -> Unit
    ) {
        retryButton.setOnClickListener { repoAdapter.retry() }

        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiActionLocat.Scroll(currentQuery = uiState.value.query))
            }
        })

        val notLoading = repoAdapter.loadStateFlow
            .asRemotePresentationState()
            .map { it == RemotePresentationState.PRESENTED }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        )
            .distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest(repoAdapter::submitData)
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) list.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            repoAdapter.loadStateFlow.collect { loadState ->
                // Show a retry header if there was an error refreshing, and items were previously
                // cached OR default to the default prepend state
                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && repoAdapter.itemCount > 0 }
                    ?: loadState.prepend

                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && repoAdapter.itemCount == 0
                // show empty list
                emptyList.isVisible = isListEmpty
                // Only show the list if refresh succeeds, either from the the local db or the remote.
                list.isVisible =
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                retryButton.isVisible =
                    loadState.mediator?.refresh is LoadState.Error && repoAdapter.itemCount == 0

            }
        }
    }

    private fun ActivityLocationsBinding.showEmptyListLocat(show: Boolean) {
        emptyList.isVisible = show
        list.isVisible = !show
    }

    enum class RemotePresentationState {
        INITIAL, REMOTE_LOADING, SOURCE_LOADING, PRESENTED
    }

    private fun Flow<CombinedLoadStates>.asRemotePresentationState(): Flow<RemotePresentationState> =
        scan(RemotePresentationState.INITIAL) { state, loadState ->
            when (state) {
                RemotePresentationState.PRESENTED -> when (loadState.mediator?.refresh) {
                    is LoadState.Loading -> RemotePresentationState.REMOTE_LOADING
                    else -> state
                }
                RemotePresentationState.INITIAL -> when (loadState.mediator?.refresh) {
                    is LoadState.Loading -> RemotePresentationState.REMOTE_LOADING
                    else -> state
                }
                RemotePresentationState.REMOTE_LOADING -> when (loadState.source.refresh) {
                    is LoadState.Loading -> RemotePresentationState.SOURCE_LOADING
                    else -> state
                }
                RemotePresentationState.SOURCE_LOADING -> when (loadState.source.refresh) {
                    is LoadState.NotLoading -> RemotePresentationState.PRESENTED
                    else -> state
                }
            }
        }
            .distinctUntilChanged()
}
