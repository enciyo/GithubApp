package com.enciyo.githubapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.enciyo.githubapp.R
import com.enciyo.githubapp.databinding.FragmentHomeBinding
import com.enciyo.githubapp.ui.adapter.UserAdapter
import com.enciyo.githubapp.ui.base.BaseFragment
import com.enciyo.githubapp.ui.ext.attach
import com.enciyo.githubapp.ui.ext.detach
import com.enciyo.githubapp.ui.ext.endlessScrollListener
import com.enciyo.githubapp.ui.ext.linearLayoutManager
import com.enciyo.githubapp.ui.search.SearchFragment
import com.enciyo.githubapp.ui.search.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home,
    vbFactory = FragmentHomeBinding::bind,
    HomeViewModel::class
) {
    var progress = 0f

    private val usersAdapter by lazy { UserAdapter(::onHandleAdapterEvent) }
    private val favoriteUsersAdapter by lazy { UserAdapter(::onHandleAdapterEvent) }

    private val concatAdapter = ConcatAdapter(
        favoriteUsersAdapter,
        usersAdapter
    )

    private val layoutManager by linearLayoutManager()
    private val endlessScrollListener by endlessScrollListener({ layoutManager }) {
        vm.loadMore()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.progress = progress

        binding.users.attach(concatAdapter, layoutManager, endlessScrollListener)

        SearchFragment.register(this){
            vm.search(it)
        }


        binding.searchBar.setOnClickListener {

            findNavController().navigate(SearchFragmentDirections.actionGlobalSearchFragment(binding.searchBar.text.toString()))
        }

        vm.state.observe(viewLifecycleOwner, ::observeState)

        vm.checkFavorites()
    }

    private fun observeState(state: HomeViewModel.HomeUiState) {
        binding.users.post {
            if (state.users.isNotEmpty() || state.favorites.isNotEmpty())
                binding.root.transitionToEnd()
            else
                binding.root.transitionToStart()
        }

        usersAdapter.submitList(state.users)
        favoriteUsersAdapter.submitList(if (state.isShowFavorites) state.favorites else emptyList())
        binding.title.text = state.getTitle(requireContext())
        binding.searchBar.setText(state.searchKeyword)
        binding.searchBar.setNavigationIcon(state.getNavigationIcon())
        binding.searchBar.setNavigationOnClickListener {
            if (state.searchKeyword.isNotEmpty()){
                vm.init()
            }
        }
    }

    private fun onHandleAdapterEvent(event: UserAdapter.Event) {
        when (event) {
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