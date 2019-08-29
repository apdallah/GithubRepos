package com.apdallahy3.cashutask.ReposatoriesScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apdallahy3.cashutask.Network.Models.ReposItem
import com.apdallahy3.cashutask.R
import com.apdallahy3.cashutask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var preLast: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)

        val viewModel = ViewModelProviders.of(this).get(repoViewModel::class.java)
        binding.viewmodel = viewModel
        binding.repoList.adapter = ReposatoriesAdapter()

        //create scroll listener to load more data when reach recyclerview end
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutmanager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = recyclerView.layoutManager!!.childCount
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val lastItem = layoutmanager.findFirstVisibleItemPosition() + visibleItemCount

                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        //to avoid multiple calls for last item
                        preLast = lastItem
                        //Load More Data
                        val nextPage = viewModel.reposatories.value!!.size.div(15).plus(1)

                        if (!viewModel.lastPage.value!!)
                            viewModel.loadMore(nextPage)

                    }
                }


            }
        }

        //set scroll listener to recyclerview
        binding.repoList.addOnScrollListener(scrollListener)


    }
}
