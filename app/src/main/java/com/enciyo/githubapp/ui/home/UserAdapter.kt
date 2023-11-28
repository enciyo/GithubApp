package com.enciyo.githubapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enciyo.domain.model.User
import com.enciyo.githubapp.R
import com.enciyo.githubapp.databinding.ItemUserBinding


class UserAdapter(private val onEvent: (Event) -> Unit) :
    ListAdapter<User, UserAdapter.UserViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : ViewHolder(binding.root) {

        fun bind(data: User) {
            binding.root.headline = data.username
            binding.root.supportingText = data.id.toString()
            binding.root.leadingImgUrl = data.imageUrl
            binding.root.trailingIcon = AppCompatResources.getDrawable(
                binding.root.context,
                if (data.isFavorite) R.drawable.vc_star_filled else R.drawable.vc_star_outline
            )
            binding.root.setOnClickListener {
                onEvent.invoke(Event.OnDetail(data))
            }

            binding.root.setTrailingIconClickListener {
                onEvent.invoke(
                    if (data.isFavorite) Event.RemoveFavorite(data) else Event.AddFavorite(
                        data
                    )
                )
            }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem //May only check isFavorite
        }
    }


    sealed interface Event {
        data class AddFavorite(val user: User) : Event
        data class RemoveFavorite(val user: User) : Event
        data class OnDetail(val user: User) : Event
    }
}