package com.example.android.codelabs.paging.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.contract.HasCustomTitle
import com.example.android.codelabs.paging.contract.HasCustomTitleString
import com.example.android.codelabs.paging.databinding.FragmentCharacterDetailBinding
import com.example.android.codelabs.paging.model.character.CharacterEntity

private const val ARG_CHARACTER = "ARG_CHARACTER"

class CharacterDetailFragment : Fragment(), HasCustomTitleString {

    lateinit var binding: FragmentCharacterDetailBinding

    private var characterEntity: CharacterEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            characterEntity = it.getParcelable(ARG_CHARACTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCharacterDetailBinding.inflate(inflater)

        with(binding) {
            tvName.text = characterEntity!!.name
            tvGender.text = characterEntity!!.gender
            tvStatus.text = characterEntity!!.status
            tvSpecies.text = characterEntity!!.species
            tvEpisode.text = characterEntity!!.episode.toString()

            if (characterEntity!!.image.isNotBlank()) {
                Glide.with(image.context)
                    .load(characterEntity!!.image)
                    .circleCrop()
                    .placeholder(R.drawable.ic_characters)
                    .error(R.drawable.ic_error_load)
                    .into(image)
            } else {
                image.setImageResource(R.drawable.ic_characters)
            }
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(characterEntity: CharacterEntity) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, characterEntity)
                }
            }
    }

    override fun getTitleString(): String {
        return characterEntity?.name.toString()
    }
}