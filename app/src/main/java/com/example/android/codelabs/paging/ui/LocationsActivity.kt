package com.example.android.codelabs.paging.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.android.codelabs.paging.Injection
import com.example.android.codelabs.paging.databinding.ActivityLocationsBinding
import com.example.android.codelabs.paging.model.LocatResult

class LocationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLocationsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this, Injection.provideLocationViewModelFactory(owner = this))
            .get(LocationsViewModel::class.java)

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        binding.list.layoutManager = GridLayoutManager(applicationContext, 2)

        binding.bindStateLocat(
            uiState = viewModel.state,
            uiActions = viewModel.accept
        )
    }

    private fun ActivityLocationsBinding.bindStateLocat(
        uiState: LiveData<UiStateLocat>,
        uiActions: (UiActionLocat) -> Unit
    ) {
        val repoAdapter = LocationsAdapter()
        list.adapter = repoAdapter

        bindSearchLocat(
            uiState = uiState,
            onQueryChanged = uiActions
        )
        bindListLocat(
            repoAdapter = repoAdapter,
            uiState = uiState,
            onScrollChanged = uiActions
        )
    }

    private fun ActivityLocationsBinding.bindSearchLocat(
        uiState: LiveData<UiStateLocat>,
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

        uiState
            .map(UiStateLocat::query)
            .distinctUntilChanged()
            .observe(this@LocationsActivity, searchRepo::setText)
    }

    private fun ActivityLocationsBinding.updateRepoListFromInputLocat(onQueryChanged: (UiActionLocat.Search) -> Unit) {
        searchRepo.text.trim().let {
            //if (it.isNotEmpty()) {
            list.scrollToPosition(0)
            onQueryChanged(UiActionLocat.Search(query = it.toString()))
            //}
        }
    }

    private fun ActivityLocationsBinding.bindListLocat(
        repoAdapter: LocationsAdapter,
        uiState: LiveData<UiStateLocat>,
        onScrollChanged: (UiActionLocat.Scroll) -> Unit
    ) {
        setupScrollListenerLocat(onScrollChanged)

        uiState
            .map(UiStateLocat::searchResult)
            .distinctUntilChanged()
            .observe(this@LocationsActivity) { result ->
                when (result) {
                    is LocatResult.Success -> {
                        showEmptyListLocat(result.data.isEmpty())
                        repoAdapter.submitList(result.data)
                    }
                    is LocatResult.Error -> {
                        Toast.makeText(
                            this@LocationsActivity,
                            "\uD83D\uDE28 Wooops $result.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
    }

    private fun ActivityLocationsBinding.showEmptyListLocat(show: Boolean) {
        emptyList.isVisible = show
        list.isVisible = !show
    }

    private fun ActivityLocationsBinding.setupScrollListenerLocat(
        onScrollChanged: (UiActionLocat.Scroll) -> Unit
    ) {
        val layoutManager = list.layoutManager as LinearLayoutManager
        list.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                onScrollChanged(
                    UiActionLocat.Scroll(
                        visibleItemCount = visibleItemCount,
                        lastVisibleItemPosition = lastVisibleItem,
                        totalItemCount = totalItemCount
                    )
                )
            }
        })
    }
}
