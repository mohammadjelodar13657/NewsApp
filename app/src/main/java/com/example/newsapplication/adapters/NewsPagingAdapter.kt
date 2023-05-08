package com.example.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.BR
import com.example.newsapplication.databinding.ListItemBinding
import com.example.newsapplication.retrofit.response.Article

class NewsPagingAdapter(
    private val adapterClickListener: AdapterClickListener
): PagingDataAdapter<Article, NewsPagingAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    inner class MyViewHolder(val viewDataBinding: ListItemBinding): RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)

        holder.viewDataBinding.setVariable(BR.article, item)

        Glide.with(holder.viewDataBinding.root)
            .load(item?.urlToImage)
            .into(holder.viewDataBinding.imageListItem)

        holder.viewDataBinding.listItemRoot.setOnClickListener {
            if (item != null) {
                adapterClickListener.clickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}