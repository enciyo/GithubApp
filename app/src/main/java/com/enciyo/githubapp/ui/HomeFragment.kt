package com.enciyo.githubapp.ui

import android.os.Bundle
import android.view.View
import com.enciyo.githubapp.R
import com.enciyo.githubapp.databinding.FragmentHomeBinding
import com.enciyo.githubapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home,
    vbFactory = FragmentHomeBinding::bind,
    HomeViewModel::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}