package com.enciyo.githubapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.enciyo.githubapp.R
import com.enciyo.githubapp.databinding.FragmentHomeBinding
import com.enciyo.githubapp.ui.adapter.UserAdapter
import com.enciyo.githubapp.ui.base.BaseFragment
import com.enciyo.githubapp.ui.ext.attach
import com.enciyo.githubapp.ui.ext.detach
import com.enciyo.githubapp.ui.ext.endlessScrollListener
import com.enciyo.githubapp.ui.ext.linearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home,
    vbFactory = FragmentHomeBinding::bind,
    HomeViewModel::class
) {
    var progress = 0f

    private val adapter by lazy { UserAdapter(::onHandleAdapterEvent) }


    private val layoutManager by linearLayoutManager()
    private val endlessScrollListener by endlessScrollListener({ layoutManager }) {
        vm.loadMore()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.progress = progress

        binding.users.attach(adapter, layoutManager, endlessScrollListener)


        binding.githubLogo.setOnClickListener {
            binding.root.transitionToEnd()
        }

        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        vm.state.observe(viewLifecycleOwner, ::observeState)


    }

    private fun observeState(state: HomeViewModel.HomeUiState) {
        adapter.submitList(state.users)
    }

    private fun onHandleAdapterEvent(event: UserAdapter.Event) {
          when(event){
              is UserAdapter.Event.AddFavorite -> vm.addFavorite(event.user)
              is UserAdapter.Event.OnDetail -> Unit
              is UserAdapter.Event.RemoveFavorite -> vm.removeFavorite(event.user)
          }
    }


    override fun onClearReferences(safeBinding: FragmentHomeBinding) {
        safeBinding.users.detach()
        progress = safeBinding.root.progress
        super.onClearReferences(safeBinding)
    }

}