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

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.model.Repo
import com.example.android.codelabs.paging.model.location.LocationDTO

/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)
    private val stars: TextView = view.findViewById(R.id.repo_stars)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val forks: TextView = view.findViewById(R.id.repo_forks)

    private var repo: LocationDTO? = null

    init {
        view.setOnClickListener {
            repo?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(repo: LocationDTO?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            language.visibility = View.GONE
            stars.text = resources.getString(R.string.unknown)
            forks.text = resources.getString(R.string.unknown)
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: LocationDTO) {
        this.repo = repo
        name.text = repo.name

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.type != null) {
            description.text = repo.type
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

        stars.text = repo.dimension
        forks.text = repo.url

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!repo.name.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            language.text = resources.getString(R.string.language, repo.name)
            languageVisibility = View.VISIBLE
        }
        language.visibility = languageVisibility
    }

    companion object {
        fun create(parent: ViewGroup): LocationViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.location_view_item, parent, false)
            return LocationViewHolder(view)
        }
    }
}
