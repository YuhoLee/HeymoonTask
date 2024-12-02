package com.project.presentation.detail

import com.project.presentation.detail.item.CollectionDetailItem

data class CollectionDetailState(
    val collectionDetailItem: CollectionDetailItem?,
    val isAddFavoriteSuccess: Boolean,
    val isLoading: Boolean,
    val errorMsg: String?
){
    companion object{
        fun create() = CollectionDetailState(
            collectionDetailItem = null,
            isAddFavoriteSuccess = false,
            isLoading = false,
            errorMsg = null
        )
    }
}
