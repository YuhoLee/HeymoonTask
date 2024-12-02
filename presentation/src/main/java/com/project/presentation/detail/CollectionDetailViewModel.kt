package com.project.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.common.data.DataState
import com.project.domain.model.LocalCollectionInsertModel
import com.project.domain.usecase.FetchFavoriteCollectionsUseCase
import com.project.domain.usecase.InsertFavoriteCollectionUseCase
import com.project.presentation.detail.item.CollectionDetailItem
import com.project.presentation.search.SearchUiState.Companion.PAGING_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    private val insertFavoriteCollectionsUseCase: InsertFavoriteCollectionUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<CollectionDetailState> =
        MutableStateFlow(CollectionDetailState.create())
    val state get() = _state

    fun onEvent(event: CollectionDetailEvent) {
        when (event) {
            is CollectionDetailEvent.AddFavoriteCollection -> {
                addFavoriteCollection(item = event.item)
            }
        }
    }

    private fun addFavoriteCollection(item: CollectionDetailItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val insertModel = LocalCollectionInsertModel(
                productNameKo = item.productNameKo,
                productNameEn = item.productNameEn,
                maker = item.maker,
                productClass = item.productClass,
                whSize = item.whSize,
                manufactureYear = item.manufactureYear,
                collectYear = item.collectYear,
                materialTechnic = item.materialTechnic,
                thumbImgUrl = item.thumbImgUrl,
                mainImgUrl = item.mainImgUrl,
                addedDatetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
            )
            insertFavoriteCollectionsUseCase(model = insertModel).collectLatest { result ->
                when (result) {
                    is DataState.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = result.isLoading
                        )
                    }

                    is DataState.Success -> {
                        _state.value = _state.value.copy(
                            isAddFavoriteSuccess = result.data ?: false
                        )
                    }

                    is DataState.Error -> {
                        _state.value = _state.value.copy(
                            errorMsg = result.message
                        )
                    }
                }

            }
        }
    }

}