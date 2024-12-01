package com.project.presentation.search

import com.project.domain.model.RemoteCollectionModel

data class SearchUiState(
    val targetKeyword: String,
    val searchResult: List<RemoteCollectionModel>?,
    val productClasses: List<ProductClass>,
    val sortType: SortType,
    val searchStartIdx: Int,
    val searchEndIdx: Int,
    val isPagingExist: Boolean,
    val isLoading: Boolean,
    val isSearchLoading: Boolean,
    val errorMsg: String?
) {
    companion object {
        const val PAGING_SIZE = 100

        fun create() = SearchUiState(
            targetKeyword = "",
            searchResult = null,
            productClasses = ProductClass.entries,
            sortType = SortType.ManufactureYearAsc,
            isPagingExist = true,
            isLoading = false,
            isSearchLoading = false,
            searchStartIdx = 1,
            searchEndIdx = PAGING_SIZE,
            errorMsg = null
        )
    }

    fun getProductClassValues() = productClasses.map { it.value }
}