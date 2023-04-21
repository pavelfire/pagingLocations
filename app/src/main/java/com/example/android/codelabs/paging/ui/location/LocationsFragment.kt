package com.example.android.codelabs.paging.ui.location

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.Injection
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.contract.HasCustomTitle
import com.example.android.codelabs.paging.databinding.ActivityLocationsBinding
import com.example.android.codelabs.paging.databinding.FragmentCharactersBinding
import com.example.android.codelabs.paging.ui.repo.ReposLoadStateAdapter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LocationsFragment : Fragment(), HasCustomTitle {

    private lateinit var binding: FragmentCharactersBinding

    private fun FragmentCharactersBinding.bindStateLocat(
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

    private fun FragmentCharactersBinding.bindSearchLocat(
        uiState: StateFlow<UiStateLocat>,
        onQueryChanged: (UiActionLocat.Search) -> Unit
    ) {
//        searchView.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_GO) {
//                updateRepoListFromInputLocat(onQueryChanged)
//                true
//            } else {
//                false
//            }
//        }
        searchView.setOnKeyListener { _, keyCode, event ->
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
                .collect()
        }
    }

    private fun FragmentCharactersBinding.updateRepoListFromInputLocat(
        onQueryChanged: (UiActionLocat.Search) -> Unit
    ) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                query.trim().let {
                    //if (it.isNotEmpty()) {
                    list.scrollToPosition(0)
                    onQueryChanged(UiActionLocat.Search(query = it.toString()))
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                query.trim().let {
                    //if (it.isNotEmpty()) {
                    list.scrollToPosition(0)
                    onQueryChanged(UiActionLocat.Search(query = it.toString()))
                }
                return false
            }
        })
    }

    private fun FragmentCharactersBinding.bindListLocat(
        header: ReposLoadStateAdapter,
        repoAdapter: LocationsAdapter,
        uiState: StateFlow<UiStateLocat>,
        pagingData: Flow<PagingData<UiModelLocat>>,
        onScrollChanged: (UiActionLocat.Scroll) -> Unit
    ) {
        //rbFemale.setOnClickListener { repoAdapter.retry() }

        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiActionLocat.Scroll(currentQuery = uiState.value.query))
            }
        })

        val notLoading = repoAdapter.loadStateFlow
            .asRemotePresentationState()
            .map { it == LocationsActivity.RemotePresentationState.PRESENTED }

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

    private fun Flow<CombinedLoadStates>.asRemotePresentationState(): Flow<LocationsActivity.RemotePresentationState> =
        scan(LocationsActivity.RemotePresentationState.INITIAL) { state, loadState ->
            when (state) {
                LocationsActivity.RemotePresentationState.PRESENTED -> when (loadState.mediator?.refresh) {
                    is LoadState.Loading -> LocationsActivity.RemotePresentationState.REMOTE_LOADING
                    else -> state
                }
                LocationsActivity.RemotePresentationState.INITIAL -> when (loadState.mediator?.refresh) {
                    is LoadState.Loading -> LocationsActivity.RemotePresentationState.REMOTE_LOADING
                    else -> state
                }
                LocationsActivity.RemotePresentationState.REMOTE_LOADING -> when (loadState.source.refresh) {
                    is LoadState.Loading -> LocationsActivity.RemotePresentationState.SOURCE_LOADING
                    else -> state
                }
                LocationsActivity.RemotePresentationState.SOURCE_LOADING -> when (loadState.source.refresh) {
                    is LoadState.NotLoading -> LocationsActivity.RemotePresentationState.PRESENTED
                    else -> state
                }
            }
        }
            .distinctUntilChanged()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // get the view model
        val viewModel = ViewModelProvider(
            this, Injection.provideLocationViewModelFactory(
                context = requireContext(),
                owner = this
            )
        )
            .get(LocationsViewModel::class.java)

        binding = FragmentCharactersBinding.inflate(inflater)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        binding.list.layoutManager = GridLayoutManager(requireContext(), 2)

        // bind the state
        binding.bindStateLocat(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )

        return binding.root
    }

    override fun getTitleRes(): Int = R.string.locations
}