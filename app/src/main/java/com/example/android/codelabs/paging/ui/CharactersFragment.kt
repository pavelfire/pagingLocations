package com.example.android.codelabs.paging.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.contract.HasCustomTitle
import com.example.android.codelabs.paging.databinding.FragmentCharactersBinding

class CharactersFragment: Fragment(), HasCustomTitle {

    private lateinit var binding: FragmentCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCharactersBinding.inflate(inflater)
        return binding.root
    }

    override fun getTitleRes(): Int = R.string.characters
}