package com.project.domain.repository

import com.project.common.data.DataState
import com.project.domain.model.LocalCollectionInsertModel
import com.project.domain.model.LocalCollectionModel
import com.project.domain.model.RemoteCollectionModel
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    suspend fun fetchRemoteCollections(
        startIdx: Int,
        endIdx: Int,
        productClass: String?,
        collectYear: String?,
        productNameKo: String?,
        productNameEn: String?
    ): Flow<DataState<List<RemoteCollectionModel>>>

    suspend fun fetchLocalCollections(): Flow<DataState<List<LocalCollectionModel>>>
    suspend fun insertLocalFavoriteCollection(model: LocalCollectionInsertModel): Flow<DataState<Boolean>>
}