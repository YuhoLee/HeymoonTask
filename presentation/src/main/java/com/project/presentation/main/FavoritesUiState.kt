package com.project.presentation.main

import com.project.domain.model.LocalCollectionModel

data class FavoritesUiState(
    val favorites: List<LocalCollectionModel>,
    val isLoading: Boolean,
    val errorMsg: String?
) {
    companion object {

        fun create() = FavoritesUiState(
            favorites = listOf(),
            isLoading = false,
            errorMsg = null
        )
    }

}