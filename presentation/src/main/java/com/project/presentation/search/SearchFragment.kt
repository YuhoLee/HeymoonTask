package com.project.presentation.search

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.presentation.R
import com.project.presentation.base.BaseFragment
import com.project.presentation.databinding.FragmentSearchBinding
import com.project.presentation.detail.item.CollectionDetailItem
import com.project.presentation.search.adapter.SearchCollectionAdapter
import com.project.presentation.search.dialog.DialogSearchClassFilter
import com.project.presentation.search.dialog.DialogSearchOrderBy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    bindingInflater = FragmentSearchBinding::inflate,
) {
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var searchCollectionAdapter: SearchCollectionAdapter
    private lateinit var dialogSearchClassFilter: DialogSearchClassFilter
    private lateinit var dialogSearchOrderBy: DialogSearchOrderBy

    override fun init() {
        super.init()

        searchCollectionAdapter = SearchCollectionAdapter {
            val collectionDetailItem = CollectionDetailItem(
                productNameKo = it.productNameKo,
                productNameEn = it.productNameEn,
                maker = it.maker,
                productClass = it.productClass,
                whSize = it.whSize,
                manufactureYear = it.manufactureYear,
                collectYear = it.collectYear,
                materialTechnic = it.materialTechnic,
                thumbImgUrl = it.thumbImgUrl,
                mainImgUrl = it.mainImgUrl,
            )

            // 수집품 아이템 누를 떄에 대한 수행
            navController.navigate(
                resId = R.id.action_search_to_collection_detail,
                args = Bundle().apply {
                    putString("prevRoute", "Search")
                    putParcelable("item", collectionDetailItem)
                }
            )
        }
        dialogSearchClassFilter = DialogSearchClassFilter()
        dialogSearchOrderBy = DialogSearchOrderBy()
    }

    override fun initView() {
        super.initView()


        binding.rvSearchResult.adapter = searchCollectionAdapter
    }

    override fun initListener() {
        super.initListener()

        binding.apply {
            tvSearch.setOnClickListener {
                // 키보드 숨기고 포커스 제거
                hideKeyboard(view = binding.etSearch)
                etSearch.clearFocus()

                if (etSearch.text.toString().isNotEmpty()) {
                    viewModel.searchKeyword(keyword = etSearch.text.toString(), isInit = true)
                } else {
                    // 검색어를 입력해주세요 팝업 띄우기

                }
            }

            rvSearchResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    if (lastVisibleItem >= totalItemCount - 1) {
                        viewModel.searchKeyword(etSearch.text.toString())
                    }
                }
            })

            etSearch.setOnFocusChangeListener { _, hasFocus ->
                tvSearch.isVisible = hasFocus
            }

            clOrderBy.setOnClickListener {
                if (!dialogSearchOrderBy.isAdded) {
                    dialogSearchOrderBy.show(
                        requireActivity().supportFragmentManager,
                        dialogSearchOrderBy.tag
                    )
                }
            }

            clClass.setOnClickListener {
                if (!dialogSearchClassFilter.isAdded) {
                    dialogSearchClassFilter.show(
                        requireActivity().supportFragmentManager,
                        dialogSearchClassFilter.tag
                    )
                }
            }

            header.setOnBackPressed {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()

        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                uiState.collectLatest {
                    withContext(Dispatchers.Main) {
                        bindUi(it)
                    }
                }
            }
        }
    }

    private fun bindUi(uiState: SearchUiState) {
        binding.apply {
            if (uiState.searchResult != null) {
                val isEmpty = uiState.searchResult.isEmpty()
                tvNoSearchResultTitle.text =
                    requireContext().getString(R.string.text_no_search_result_title)
                        .replace("#VALUE#", etSearch.text.toString())
                clNoSearchResult.isVisible = isEmpty
                rvSearchResult.isVisible = !isEmpty
                clOrderBy.isVisible = !isEmpty
                clClass.isVisible = !isEmpty

                val pcValues = uiState.getProductClassValues()
                searchCollectionAdapter.updateList(uiState.searchResult.filter {
                    pcValues.contains(
                        it.productClass
                    )
                })
                rvSearchResult.post {
                    searchCollectionAdapter.notifyDataSetChanged()
                }
            } else {
                // 빈 화면이 출력되도록 함
                rvSearchResult.isVisible = false
                clOrderBy.isVisible = false
                clClass.isVisible = false
            }

            // 로딩 상태 + 첫 페이지 검색인 경우에만 로딩 화면 띄워줌
            clLoading.isVisible = uiState.isSearchLoading && uiState.searchResult == null

            tvOrderBy.text = requireContext().getString(uiState.sortType.strId)

            tvClass.text = when (uiState.productClasses.size) {
                0, ProductClass.entries.size -> {
                    clClass.isSelected = false
                    tvClass.isSelected = false
                    ivClass.isSelected = false
                    requireContext().getString(R.string.text_search_sector)
                }

                1 -> {
                    clClass.isSelected = true
                    tvClass.isSelected = true
                    ivClass.isSelected = true
                    requireContext().getString(uiState.productClasses[0].strId)
                }

                else -> {
                    clClass.isSelected = true
                    tvClass.isSelected = true
                    ivClass.isSelected = true
                    val replaceValueStr =
                        requireContext().getString(uiState.productClasses[0].strId)
                    val replaceCount = uiState.productClasses.size - 1
                    val str = requireContext().getString(R.string.text_search_class_explanation)
                        .replace("#VALUE#", replaceValueStr).replace("#COUNT#", "$replaceCount")
                    str
                }
            }
        }
    }
}