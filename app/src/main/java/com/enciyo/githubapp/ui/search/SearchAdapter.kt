package com.enciyo.githubapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enciyo.domain.model.User
import com.enciyo.githubapp.databinding.ItemSearchBinding

class SearchAdapter(private val onEvent: (Event) -> Unit) :
    ListAdapter<User, SearchAdapter.SearchViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: User) {
            binding.root.headline = data.username
            binding.root.setOnClickListener {
                onEvent.invoke(Event.OnDetail(data))
            }
            binding.root.setTrailingIconClickListener {
                onEvent.invoke(Event.OnMoveUp(data))
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
        data class OnMoveUp(val user: User) : Event
        data class OnDetail(val user: User) : Event
    }
}