package com.project.presentation.detail

import com.project.presentation.detail.item.CollectionDetailItem

sealed class CollectionDetailEvent {
    data class AddFavoriteCollection(val item: CollectionDetailItem): CollectionDetailEvent()
}