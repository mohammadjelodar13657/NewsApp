package com.example.newsapplication

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.newsapplication.paging.NewsPagingSource
import com.example.newsapplication.retrofit.NewsInterface
import com.example.newsapplication.retrofit.response.Article
import kotlinx.coroutines.flow.Flow

class NewsRepository(private val newsInterface: NewsInterface) {

    fun getAllNewsStream(): LiveData<PagingData<Article>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            NewsPagingSource(newsInterface)
        }
    ).liveData

}