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

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.model.character.CharacterEntity

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val image: ImageView = view.findViewById(R.id.image)
    private val name: TextView = view.findViewById(R.id.tvName)
    private val tvSpecies: TextView = view.findViewById(R.id.tvSpecies)
    private val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    private val tvGender: TextView = view.findViewById(R.id.tvGender)

    private var repo: CharacterEntity? = null

    /*
    init {
        view.setOnClickListener {
//            repo?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
            repo?.let {
                Log.d("MyTAG", "repo = $repo")
            }
        }
    }*/

    fun bind(repo: CharacterEntity?, un: CharactersAdapter.OnCharacterListener) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            tvSpecies.visibility = View.GONE
            tvStatus.visibility = View.GONE
            tvGender.text = resources.getString(R.string.unknown)
        } else {
            showRepoData(repo, un)
        }
    }

    private fun showRepoData(repo: CharacterEntity, un: CharactersAdapter.OnCharacterListener) {
        this.repo = repo
        name.text = repo.name

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.type != null) {
            tvSpecies.text = repo.type
            descriptionVisibility = View.VISIBLE
        }
        tvSpecies.visibility = descriptionVisibility

        tvStatus.text = repo.gender
        tvGender.text = repo.url

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!repo.name.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            tvGender.text = resources.getString(R.string.language, repo.name)
            languageVisibility = View.VISIBLE
        }
        tvSpecies.visibility = languageVisibility

        if (repo.image.isNotBlank()) {
            Glide.with(image.context)
                .load(repo.image)
                .circleCrop()
                .placeholder(R.drawable.ic_characters)
                .error(R.drawable.ic_error_load)
                .into(image)
        } else {
            image.setImageResource(R.drawable.ic_characters)
        }
        itemView.setOnClickListener {
            un.onCharacterClick(repo)
            Log.d("MyTAG", "itemView clicked")
        }
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.character_item, parent, false)
            return CharacterViewHolder(view)
        }
    }
}
