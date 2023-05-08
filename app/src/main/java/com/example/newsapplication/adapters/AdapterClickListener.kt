package com.example.newsapplication.adapters

import com.example.newsapplication.retrofit.response.Article

interface AdapterClickListener {

    fun clickListener(article: Article)

}