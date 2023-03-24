package com.example.android.codelabs.paging.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android.codelabs.paging.model.location.LocationDTO

/**
 * Adapter for the list of repositories.
 */
class LocationsAdapter : ListAdapter<LocationDTO, LocationViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<LocationDTO>() {
            override fun areItemsTheSame(oldItem: LocationDTO, newItem: LocationDTO): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: LocationDTO, newItem: LocationDTO): Boolean =
                oldItem == newItem
        }
    }
}
