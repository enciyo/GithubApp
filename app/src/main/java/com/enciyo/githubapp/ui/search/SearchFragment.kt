package com.enciyo.githubapp.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import com.enciyo.githubapp.R
import com.enciyo.githubapp.databinding.FragmentSearchBinding
import com.enciyo.githubapp.ui.base.BaseFragment
import com.enciyo.githubapp.ui.ext.attach
import com.enciyo.githubapp.ui.ext.detach
import com.enciyo.githubapp.ui.ext.endlessScrollListener
import com.enciyo.githubapp.ui.ext.flowTextWatcher
import com.enciyo.githubapp.ui.ext.linearLayoutManager
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    R.layout.fragment_search,
    FragmentSearchBinding::bind,
    SearchViewModel::class
) {
    private val args by navArgs<SearchFragmentArgs>()

    private val searchAdapter by lazy { SearchAdapter(::onHandleAdapterEvent) }
    private val layoutManager by linearLayoutManager()
    private val endlessScrollListener by endlessScrollListener({ layoutManager }) {
        vm.loadMore()
    }

    private val concatAdapter by lazy {
        ConcatAdapter(
            searchAdapter
        )
    }

    private fun onHandleAdapterEvent(event: SearchAdapter.Event) {
        when (event) {
            is SearchAdapter.Event.OnDetail -> {
                SearchFragmentDirections.actionGlobalUserDetailFragment(event.user.username).navigate()
            }

            is SearchAdapter.Event.OnMoveUp -> {
                binding.searchView.editText.setText(event.user.username)
                binding.searchView.editText.setSelection(event.user.username.lastIndex)
            }
        }
    }


    companion object {
        const val TAG = "SearchFragment"
        const val SEARCH_RESULT = TAG + "SearchResult"

        fun register(fragment: Fragment, onQuery: (String) -> Unit) {
            fragment.setFragmentResultListener(TAG) { _: String, bundle: Bundle ->
                onQuery.invoke(bundle.getString(SEARCH_RESULT, ""))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setVisible(true)
        binding.searchView.addTransitionListener { _, _, newState ->
            if (newState == SearchView.TransitionState.HIDING) {
                findNavController().popBackStack()
            }
        }
        binding.searchView.setText(args.keyword)


        binding.searchView
            .editText
            .setOnEditorActionListener { v, actionId, event ->
                setResultAndDismiss()
                false
            }

        binding.searches.attach(concatAdapter,layoutManager,endlessScrollListener)

        binding.searchView.editText
            .flowTextWatcher()
            .onEach {
                vm.search(it)
            }
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .launchIn(viewLifecycleOwner.lifecycleScope)


        vm.state.observe(viewLifecycleOwner,){
            searchAdapter.submitList(it.users)
        }
    }


    override fun onClearReferences(safeBinding: FragmentSearchBinding) {
        safeBinding.searches.detach()
        super.onClearReferences(safeBinding)
    }
    private fun setResultAndDismiss() {
        setFragmentResult(TAG, bundleOf(SEARCH_RESULT to binding.searchView.text.toString()))
        findNavController().popBackStack()
    }

}