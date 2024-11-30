package com.project.presentation.search

import androidx.paging.PagingData
import com.project.domain.model.RemoteCollectionModel

data class SearchUiState(
    val targetKeyword: String,
    val searchResult: PagingData<RemoteCollectionModel>?,
    val searchFilter: SearchFilter,
    val isLoading: Boolean,
    val errorMsg: String?
) {
    companion object {
        fun create() = SearchUiState(
            targetKeyword = "",
            searchResult = null,
            searchFilter = SearchFilter.create(),
            isLoading = false,
            errorMsg = null
        )
    }
}