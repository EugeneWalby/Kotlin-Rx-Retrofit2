package com.chisw.taskbyandrew.network

import com.chisw.taskbyandrew.BuildConfig
import com.chisw.taskbyandrew.network.model.Model
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlgoliaApiService {
    @GET("search_by_date")
    fun getStoriesInfo(@Query("page") page: Int,
                       @Query("tags") tags: String): Single<Model.HitsResponse>

    @GET("users/{username}")
    fun getAuthorInfo(@Path("username") username: String): Single<Model.UserResponse>

    companion object {
        fun create(): AlgoliaApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()

            return retrofit.create(AlgoliaApiService::class.java)
        }
    }
}