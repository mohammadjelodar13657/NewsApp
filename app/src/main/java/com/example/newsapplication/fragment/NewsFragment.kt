package com.example.newsapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.NewsViewModel
import com.example.newsapplication.R
import com.example.newsapplication.adapters.AdapterClickListener
import com.example.newsapplication.adapters.NewsPagingAdapter
import com.example.newsapplication.databinding.FragmentNewsBinding
import com.example.newsapplication.retrofit.response.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment(), AdapterClickListener {

    private val viewModel by viewModels<NewsViewModel>()

    private lateinit var binding: FragmentNewsBinding

    private lateinit var newsPagingAdapter: NewsPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsPagingAdapter = NewsPagingAdapter(this)

        viewModel.list.observe(viewLifecycleOwner) {
            newsPagingAdapter.submitData(lifecycle, it)
        }

        newsPagingAdapter.addLoadStateListener {

            when(it.refresh) {
                is LoadState.Loading -> {
                    binding.newsProgress.visibility = View.VISIBLE
                }
                is LoadState.NotLoading -> {
                    binding.newsProgress.visibility = View.GONE
                }
                is LoadState.Error -> {
                    binding.newsProgress.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.newsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.newsRecycler.adapter = newsPagingAdapter
    }

    override fun clickListener(article: Article) {
        findNavController().navigate(R.id.action_newsFragment_to_detailsFragment, bundleOf("article" to article))
    }

}