package com.project.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.common.data.DataState
import com.project.domain.model.RemoteCollectionModel
import com.project.domain.usecase.FetchSearchCollectionsUseCase
import com.project.presentation.search.SearchUiState.Companion.PAGING_SIZE
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

    fun updateOrderBy(type: SortType){
        val sortedList = _uiState.value.searchResult.sortBySortType(type)
        _uiState.value = _uiState.value.copy(
            sortType = type,
            searchResult = sortedList
        )
    }

    fun updateProductClassesFilter(list: List<ProductClass>){
        // 아무것도 선택되지 않았을 때는 모든 필터를 다 선택하도록 함
        val inputClasses = list.ifEmpty { ProductClass.entries }

        _uiState.value = _uiState.value.copy(
            productClasses = inputClasses,
        )
    }

    fun initErrorMsg(){
        _uiState.value = _uiState.value.copy(
            errorMsg = null
        )
    }

    fun searchKeyword(keyword: String, isInit: Boolean = false) {
        viewModelScope.launch {
            if(isInit){
                _uiState.value = _uiState.value.copy(
                    searchResult = null,
                    searchStartIdx = 1,
                    searchEndIdx = PAGING_SIZE
                )
            }

            // 로딩 중일때는 요청되지 않도록함
            if(uiState.value.isSearchLoading) return@launch

            fetchSearchCollectionsUseCase(
                startIdx = _uiState.value.searchStartIdx,
                endIdx = _uiState.value.searchEndIdx,
                productNameKo = keyword,
            ).collectLatest { result ->
                when (result) {
                    is DataState.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isSearchLoading = result.isLoading
                        )
                    }

                    is DataState.Success -> {
                        val resList = if(uiState.value.searchResult != null){
                            uiState.value.searchResult!!.plus(result.data.sortBySortType(uiState.value.sortType))
                        }else{
                            result.data ?: listOf()
                        }
                        _uiState.value = _uiState.value.copy(
                            searchResult = resList,
                            searchStartIdx = _uiState.value.searchEndIdx + 1,
                            searchEndIdx = _uiState.value.searchEndIdx + PAGING_SIZE,
                            errorMsg = null
                        )
                    }

                    is DataState.Error -> {
                        _uiState.value = _uiState.value.copy(
                            errorMsg = result.message
                        )
                    }
                }

            }
        }
    }

    private fun List<RemoteCollectionModel>?.sortBySortType(type: SortType) =
        when(type) {
            SortType.ManufactureYearAsc -> {
                this?.sortedBy { it.manufactureYear }
            }

            else -> {
               this?.sortedByDescending { it.manufactureYear }
            }
        } ?: listOf()

}