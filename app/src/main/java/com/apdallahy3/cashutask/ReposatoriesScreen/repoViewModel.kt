package com.apdallahy3.cashutask.ReposatoriesScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apdallahy3.cashutask.Network.Models.ReposItem
import com.apdallahy3.marvelcharcters.Network.GithubAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

enum class GithubAPIStatus { LOADING, ERROR, DONE }

class repoViewModel : ViewModel() {
    //make a job to be used with coroutines
    private var viewModelJob = Job()
    //set coroutines scope
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    //encapsulate reposatories list can't be changed from fragment
    private val _reposatories = MutableLiveData<List<ReposItem>>()
    //make a read only reposatories list to be read from fragment
    val reposatories: LiveData<List<ReposItem>>
        get() = _reposatories
    //Call Statues
    private val _statues = MutableLiveData<GithubAPIStatus>()
    val statues: LiveData<GithubAPIStatus>
        get() = _statues
    //last page
    private val _lastPage=MutableLiveData<Boolean>()
      val lastPage:LiveData<Boolean>
    get() = _lastPage
    init {
        _lastPage.value=false
        getRepos(1)
    }

    fun getRepos(page: Int) {
        coroutineScope.launch {
            var getCharactersDeferred =
                GithubAPI.retrofitService.getReposatories(page, 15)
            try {
                _statues.value = GithubAPIStatus.LOADING

                var listResult = getCharactersDeferred.await()
                if (listResult.size > 0) {
                    _reposatories.value = listResult
                    _statues.value = GithubAPIStatus.DONE
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _statues.value = GithubAPIStatus.ERROR
                _reposatories.value = ArrayList()
            }
        }

    }
    val loadinItem = ReposItem(-1,"loading","loading",false, Date(),Date(),"empty")
    fun loadMore(page: Int) {
        coroutineScope.launch {
            var getCharactersDeferred =
                GithubAPI.retrofitService.getReposatories(page, 15)
            try {
                _reposatories.value = _reposatories.value?.plus(loadinItem)


                var listResult = getCharactersDeferred.await()
                if (listResult.size > 0) {
                    _reposatories.value = _reposatories.value?.minus(loadinItem)
                    _reposatories.value = _reposatories.value?.plus(listResult)


                }else if(listResult.size==0){
                    _reposatories.value = _reposatories.value?.minus(loadinItem)
                    _lastPage.value=true
                }

            } catch (e: Exception) {
                _reposatories.value = _reposatories.value?.minus(loadinItem)
                Log.i("",e.message)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()

    }
}