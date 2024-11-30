package com.project.domain.repository

import androidx.paging.PagingData
import com.project.common.data.DataState
import com.project.domain.model.LocalCollectionInsertModel
import com.project.domain.model.LocalCollectionModel
import com.project.domain.model.RemoteCollectionModel
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    suspend fun fetchRemoteCollections(
        productClass: String?,
        collectYear: String?,
        productNameKo: String?,
        productNameEn: String?
    ): Flow<PagingData<RemoteCollectionModel>>

    suspend fun fetchLocalCollections(): Flow<DataState<List<LocalCollectionModel>>>
    suspend fun insertLocalFavoriteCollection(model: LocalCollectionInsertModel): Flow<DataState<Boolean>>
}