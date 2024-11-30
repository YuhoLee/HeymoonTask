package com.project.presentation.search

import android.content.res.Configuration
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.project.presentation.R
import com.project.presentation.base.BaseFragment
import com.project.presentation.databinding.FragmentSearchBinding
import com.project.presentation.search.adapter.CollectionLoadStateAdapter
import com.project.presentation.search.adapter.SearchCollectionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    bindingInflater = FragmentSearchBinding::inflate,
) {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchCollectionAdapter: SearchCollectionAdapter

    override fun init() {
        super.init()
    }

    override fun initView() {
        super.initView()

        searchCollectionAdapter = SearchCollectionAdapter().apply {
            withLoadStateFooter(
                footer = CollectionLoadStateAdapter { this.retry() }
            )
        }
        binding.rvSearchResult.adapter = searchCollectionAdapter
    }

    override fun initListener() {
        super.initListener()

        binding.apply {
            tvSearch.setOnClickListener {
                if (etSearch.text.toString().isNotEmpty()) {
                    viewModel.searchKeyword(keyword = etSearch.text.toString())
                } else {

                }
            }

            searchCollectionAdapter.addLoadStateListener { combinedLoadStates ->
                if (combinedLoadStates.append.endOfPaginationReached) {
                    // 아이템 수가 1보다 작으면 Empty View 보이도록 처리

                }
            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()

        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                uiState.collectLatest {
                    if (it.searchResult != null) {
                        searchCollectionAdapter.submitData(it.searchResult)
                    }
                }

            }
        }

        lifecycleScope.launch {
            searchCollectionAdapter.loadStateFlow.collectLatest { loadStates ->
                Log.d("TAG", "observeViewModel: ${loadStates}")
                val isEmpty =
                    loadStates.refresh is LoadState.NotLoading && searchCollectionAdapter.itemCount == 0
                binding.apply {
                    pbLoading.isVisible = loadStates.refresh is LoadState.Loading
                    clNoSearchResult.isVisible = isEmpty
                    rvSearchResult.isVisible = !isEmpty
                    if (isEmpty) {
                        tvNoSearchResultTitle.text =
                            requireContext().getString(R.string.text_no_search_result_title)
                                .replace("#VALUE#", etSearch.text.toString())
                    }
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }
}