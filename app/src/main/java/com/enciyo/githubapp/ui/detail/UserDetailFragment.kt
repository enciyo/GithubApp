package com.enciyo.githubapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.enciyo.domain.model.UserDetail
import com.enciyo.githubapp.R
import com.enciyo.githubapp.databinding.FragmentUserDetailBinding
import com.enciyo.githubapp.ui.base.BaseFragment
import com.enciyo.shared.openTwitter
import com.enciyo.shared.openUrl
import com.enciyo.shared.share
import com.enciyo.shared.toUiDate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserDetailFragment : BaseFragment<FragmentUserDetailBinding, UserDetailViewModel>(
    R.layout.fragment_user_detail,
    FragmentUserDetailBinding::bind,
    UserDetailViewModel::class
) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.detail.observe(viewLifecycleOwner, ::observeDetail)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeDetail(detail: UserDetail) {
        Glide.with(binding.root).load(detail.imageUrl).into(binding.image)
        binding.name.text = detail.name
        binding.username.text = detail.username
        binding.company.text = detail.company
        binding.followersStatic.text =
            getString(R.string.followersAndFollowing, detail.followersCount, detail.followingCount)
        binding.bio.text = detail.bio
        binding.itemRepo.trailingSupportingText = detail.reposCount.toString()
        binding.itemGist.trailingSupportingText = detail.gistsCount.toString()
        binding.itemCreate.trailingSupportingText = detail.createdTime.toUiDate()
        binding.itemUpdate.trailingSupportingText = detail.updatedTime.toUiDate()
        binding.twitter.setOnClickListener {
            requireContext().openTwitter(detail.twitterUsername)
        }
        binding.blog.setOnClickListener {
            requireContext().openUrl(detail.blogUrl)
        }
        binding.share.setOnClickListener {
            requireContext().share(detail.shareLink)
        }
        binding.toolbar.menu.findItem(R.id.star).icon = ContextCompat
            .getDrawable(
                requireContext(),
                if (detail.isFavorite) R.drawable.vc_star_filled else R.drawable.vc_star_outline
            )
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.star) {
                if (detail.isFavorite)
                    vm.deleteFavorite(detail)
                else
                    vm.addFavorite(detail)
            }
            false
        }
    }


}

