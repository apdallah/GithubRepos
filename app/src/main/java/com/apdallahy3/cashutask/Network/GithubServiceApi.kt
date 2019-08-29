package com.apdallahy3.marvelcharcters.Network

import com.apdallahy3.cashutask.Network.Models.ReposItem
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://api.github.com/users/JakeWharton/"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor()
        .apply
        { HttpLoggingInterceptor.Level.BODY })
    .readTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(60, TimeUnit.SECONDS)


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client.build())
    .build()

interface GithubServiceApi {

    @GET("repos")
    fun getReposatories(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int

    ):
            Deferred<List<ReposItem>>


}

object GithubAPI {

    val retrofitService: GithubServiceApi by lazy {
        retrofit.create(GithubServiceApi::class.java)
    }

}