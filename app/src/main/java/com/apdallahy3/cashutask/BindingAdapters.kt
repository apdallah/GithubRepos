package com.apdallahy3.cashutask

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apdallahy3.cashutask.Network.Models.ReposItem
import com.apdallahy3.cashutask.ReposatoriesScreen.GithubAPIStatus
import com.apdallahy3.cashutask.ReposatoriesScreen.ReposatoriesAdapter
import com.apdallahy3.marvelcharcters.Network.GithubAPI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("recyclerData")
fun bindRecyclerView(recycler: RecyclerView, list: List<ReposItem>?) {
    val adapter = recycler.adapter as ReposatoriesAdapter
    adapter.submitList(list)
}


@BindingAdapter("apiStatues")
fun bindApiStatus(statusImageView: ImageView, statues: GithubAPIStatus?) {
    Log.i("Binding", "" + statues)
    when (statues) {
        GithubAPIStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading)
        }
        GithubAPIStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)

        }
        GithubAPIStatus.DONE -> {
            statusImageView.visibility = View.GONE

        }
    }
}