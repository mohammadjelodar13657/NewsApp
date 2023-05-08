package com.example.newsapplication.retrofit

import com.example.newsapplication.retrofit.response.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    // https://newsapi.org/v2/everything?q=tesla&from=2023-04-07&sortBy=publishedAt&apiKey=2e63336cf3414e0c8c8d3599b3c8935b

    @GET("/v2/everything")
    suspend fun getAllNews(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse
}