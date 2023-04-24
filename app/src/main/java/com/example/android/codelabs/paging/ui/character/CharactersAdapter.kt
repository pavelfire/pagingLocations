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

package com.example.android.codelabs.paging.ui.character

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.model.character.CharacterEntity
import com.example.android.codelabs.paging.ui.repo.SeparatorViewHolder

class CharactersAdapter(
    private val actionListener: OnCharacterListener
) : PagingDataAdapter<UiModelCharact, RecyclerView.ViewHolder>(
    UIMODEL_COMPARATOR
),
    View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return if (viewType == R.layout.repo_view_item) {
//            LocationViewHolder.create(parent)
//        } else {
//            SeparatorViewHolder.create(parent)
//        }
        return CharacterViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModelCharact.LocatItem -> R.layout.location_view_item
            is UiModelCharact.SeparatorItem -> R.layout.separator_view_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModelLocat = getItem(position)
        uiModelLocat.let {
            when (uiModelLocat) {
                is UiModelCharact.LocatItem -> {
                    holder.itemView.setOnClickListener {
                        actionListener.onCharacterClick(uiModelLocat.locat)
                    }

                    (holder as CharacterViewHolder).bind(uiModelLocat.locat)
                }
                is UiModelCharact.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModelLocat.description)
                else -> Exception()
            }
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModelCharact>() {
            override fun areItemsTheSame(
                oldItem: UiModelCharact,
                newItem: UiModelCharact
            ): Boolean {
                return (oldItem is UiModelCharact.LocatItem && newItem is UiModelCharact.LocatItem &&
                        oldItem.locat.name == newItem.locat.name) ||
                        (oldItem is UiModelCharact.SeparatorItem && newItem is UiModelCharact.SeparatorItem &&
                                oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(
                oldItem: UiModelCharact,
                newItem: UiModelCharact
            ): Boolean =
                oldItem == newItem
        }
    }

    interface OnCharacterListener {
        fun onCharacterClick(character: CharacterEntity)
    }

    override fun onClick(view: View) {
        val character = view.tag as CharacterEntity
        actionListener.onCharacterClick(character)
    }
}
