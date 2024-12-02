package com.project.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.common.data.DataState
import com.project.domain.usecase.FetchFavoriteCollectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val fetchFavoriteCollectionsUseCase: FetchFavoriteCollectionsUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<FavoritesUiState> =
        MutableStateFlow(FavoritesUiState.create())
    val uiState get() = _uiState.asStateFlow()

    fun fetchFavoriteCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchFavoriteCollectionsUseCase().collectLatest { result ->
                when (result) {
                    is DataState.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = result.isLoading
                        )
                    }

                    is DataState.Success -> {
                        _uiState.value = _uiState.value.copy(
                            favorites = result.data ?: listOf(),
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
}