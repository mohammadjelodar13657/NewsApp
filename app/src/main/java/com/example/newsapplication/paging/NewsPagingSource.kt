package com.example.newsapplication.paging

import android.icu.text.Transliterator
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.util.newStringBuilder
import com.example.newsapplication.Constants
import com.example.newsapplication.retrofit.NewsInterface
import com.example.newsapplication.retrofit.response.Article
import kotlinx.coroutines.flow.single
import retrofit2.HttpException
import java.io.Console
import java.io.IOException

const val STARTING_INDEX = 1

class NewsPagingSource(val newsInterface: NewsInterface): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            val anchorPage =state?.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val position = params.key?: STARTING_INDEX



        return try {
            val data = newsInterface.getAllNews(Constants.q, "", "", Constants.API_KEY, position, params.loadSize)
            LoadResult.Page(
                data = data.articles,
                prevKey = if(params.key == STARTING_INDEX) null else position - 1,
                nextKey = if(data.articles.isEmpty()) null else position + 1
            )
        } catch (e:IOException) {
            LoadResult.Error(e)
        } catch (e:HttpException) {
            LoadResult.Error(e)
        }
    }
}