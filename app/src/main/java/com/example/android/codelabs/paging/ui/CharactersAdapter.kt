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

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.ui.location.LocationViewHolder
import com.example.android.codelabs.paging.ui.location.UiModelLocat
import com.example.android.codelabs.paging.ui.repo.SeparatorViewHolder

class CharactersAdapter : PagingDataAdapter<UiModelLocat, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return if (viewType == R.layout.repo_view_item) {
//            LocationViewHolder.create(parent)
//        } else {
//            SeparatorViewHolder.create(parent)
//        }
        return LocationViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModelLocat.LocatItem -> R.layout.location_view_item
            is UiModelLocat.SeparatorItem -> R.layout.separator_view_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModelLocat = getItem(position)
        uiModelLocat.let {
            when (uiModelLocat) {
                is UiModelLocat.LocatItem -> (holder as LocationViewHolder).bind(uiModelLocat.locat)
                is UiModelLocat.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModelLocat.description)
                else -> Exception()
            }
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModelLocat>() {
            override fun areItemsTheSame(oldItem: UiModelLocat, newItem: UiModelLocat): Boolean {
                return (oldItem is UiModelLocat.LocatItem && newItem is UiModelLocat.LocatItem &&
                        oldItem.locat.name == newItem.locat.name) ||
                        (oldItem is UiModelLocat.SeparatorItem && newItem is UiModelLocat.SeparatorItem &&
                                oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(oldItem: UiModelLocat, newItem: UiModelLocat): Boolean =
                oldItem == newItem
        }
    }
}
