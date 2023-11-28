package com.enciyo.githubapp.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.enciyo.githubapp.R
import com.enciyo.githubapp.databinding.FragmentSearchBinding
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding<FragmentSearchBinding>()
    private val args by navArgs<SearchFragmentArgs>()

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
    }

    private fun setResultAndDismiss() {
        setFragmentResult(TAG, bundleOf(SEARCH_RESULT to binding.searchView.text.toString()))
        findNavController().popBackStack()
    }

}