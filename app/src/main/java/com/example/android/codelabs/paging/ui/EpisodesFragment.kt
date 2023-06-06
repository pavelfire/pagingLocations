package com.example.android.codelabs.paging.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.contract.HasCustomTitle
import com.example.android.codelabs.paging.databinding.FragmentEpisodesBinding
import com.example.android.codelabs.paging.ui.about.AboutFragment

class EpisodesFragment: Fragment(), HasCustomTitle {

    private lateinit var binding: FragmentEpisodesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEpisodesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentManager = activity?.supportFragmentManager
        val parentFragmentManager = parentFragmentManager

        parentFragmentManager.beginTransaction()
            .add(R.id.fragment_container, AboutFragment())
            .setReorderingAllowed(true)
            .addToBackStack("myStack")
            .commit();
    }

    override fun getTitleRes(): Int = R.string.episodes
}