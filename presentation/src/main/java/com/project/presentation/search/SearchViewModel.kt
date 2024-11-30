package com.project.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.project.domain.model.RemoteCollectionModel
import com.project.domain.usecase.FetchSearchCollectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fetchSearchCollectionsUseCase: FetchSearchCollectionsUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState.create())
    val uiState get() = _uiState.asStateFlow()

    fun searchKeyword(keyword: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    errorMsg = null
                )

                fetchSearchCollectionsUseCase(
                    productNameKo = keyword
                ).cachedIn(viewModelScope).collectLatest { result ->
                    _uiState.value = _uiState.value.copy(
                        searchResult = result,
                        isLoading = false,
                        errorMsg = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMsg = e.localizedMessage
                )
            }
        }
    }
//
//    fun PagingData<RemoteCollectionModel>.filterAndSort(): PagingData<RemoteCollectionModel> {
//        val filter = _uiState.value.searchFilter
//        val resultList = mutableListOf<RemoteCollectionModel>()
//
//        this.filter { model ->
//            // 필터 조건
//            val classValues = filter.productClasses.map { it.value }
//            if(filter.productClasses.isEmpty()){
//                true
//            }else{
//                classValues.contains(model.productClass)
//            }
//        }.map { item ->
//            resultList.add(item)
//        }
//        return PagingData.from(resultList)
//    }
}