package com.example.android.codelabs.paging.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.contract.HasCustomTitle
import com.example.android.codelabs.paging.databinding.FragmentEpisodesBinding

class EpisodesFragment: Fragment(), HasCustomTitle {

    private lateinit var binding: FragmentEpisodesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEpisodesBinding.inflate(inflater)
        return binding.root
    }

    override fun getTitleRes(): Int = R.string.episodes
}