package com.apdallahy3.cashutask.ReposatoriesScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apdallahy3.cashutask.Network.Models.ReposItem
import com.apdallahy3.cashutask.databinding.RepoListItemBinding
import com.apdallahy3.cashutask.databinding.RepoListItemLoadingBinding

class ReposatoriesAdapter() : ListAdapter<ReposItem, RecyclerView.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ReposItem>() {
        override fun areContentsTheSame(oldItem: ReposItem, newItem: ReposItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: ReposItem, newItem: ReposItem): Boolean {
            return oldItem == newItem
        }

    }


    val loading = 1
    val noraml = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            noraml -> viewHolder = ListViewHolder(RepoListItemBinding.inflate(LayoutInflater.from(parent.context)))
            loading-> viewHolder = LoadingViewHolder(RepoListItemLoadingBinding.inflate(LayoutInflater.from(parent.context)))

            else -> {
                viewHolder = ListViewHolder(RepoListItemBinding.inflate(LayoutInflater.from(parent.context)))
            }
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item.id == -1) {
            return loading
        } else {
            return noraml
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = getItem(position)

        if (holder is ListViewHolder) {
            holder.bind(item) as?ListViewHolder

        }else if(holder is ListViewHolder){
            holder.bind(item) as?LoadingViewHolder

        }
    }

    class ListViewHolder(private var binding: RepoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReposItem) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
    class LoadingViewHolder(private var binding: RepoListItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReposItem) {
            Log.i("Loading",""+item.id)
             binding.itemLoading = item
             binding.executePendingBindings()
        }
    }
}