package com.project.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource.LoadResult
import com.project.common.data.DataState
import com.project.common.extension.Extension.orOneSpaceEmpty
import com.project.data.local.db.FavoriteCollectionDatabase
import com.project.data.mapper.Mapper.toFavoriteCollectionEntity
import com.project.data.mapper.Mapper.toLocalCollectionModel
import com.project.data.mapper.Mapper.toRemoteCollectionModel
import com.project.data.paging.CollectionPagingSource
import com.project.data.remote.NetworkUtils
import com.project.data.remote.datasource.CollectionDataSource
import com.project.domain.model.LocalCollectionInsertModel
import com.project.domain.model.LocalCollectionModel
import com.project.domain.model.RemoteCollectionModel
import com.project.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionDataSource: CollectionDataSource,
    private val favoriteCollectionDatabase: FavoriteCollectionDatabase
) : CollectionRepository {
    /** Remote */

    override suspend fun fetchRemoteCollections(
        startIdx: Int,
        endIdx: Int,
        productClass: String?,
        collectYear: String?,
        productNameKo: String?,
        productNameEn: String?
    ): Flow<DataState<List<RemoteCollectionModel>>> {
        return flow {
            emit(DataState.Loading(isLoading = true))
            try {
                val response = NetworkUtils.safeApiCall {
                    collectionDataSource.fetchCollections(
                        startIdx = startIdx,
                        endIdx = endIdx,
                        productClass = productClass.orOneSpaceEmpty(),
                        collectYear = collectYear.orOneSpaceEmpty(),
                        productNameKo = productNameKo.orOneSpaceEmpty(),
                        productNameEn = productNameEn.orOneSpaceEmpty(),
                    )
                }
                val list = response?.res?.collectionList?.toRemoteCollectionModel() ?: listOf()
                emit(DataState.Success(data = list))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            } finally {
                emit(DataState.Loading(isLoading = false))
            }
        }
    }


    /** Local */

    override suspend fun fetchLocalCollections(): Flow<DataState<List<LocalCollectionModel>>> {
        return flow {
            emit(DataState.Loading(isLoading = true))
            try {
                val collections =
                    favoriteCollectionDatabase.getFavoriteCollectionDao().getFavoriteCollection()
                emit(DataState.Success(collections.toLocalCollectionModel()))
            } catch (e: RuntimeException) {
                emit(DataState.Error(e))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            } finally {
                emit(DataState.Loading(isLoading = false))
            }
        }
    }

    override suspend fun insertLocalFavoriteCollection(model: LocalCollectionInsertModel): Flow<DataState<Boolean>> {
        return flow {
            emit(DataState.Loading(isLoading = true))
            try {
                val res = favoriteCollectionDatabase.getFavoriteCollectionDao()
                    .insert(model.toFavoriteCollectionEntity())
                emit(DataState.Success(data = res != -1L))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            } finally {
                emit(DataState.Loading(isLoading = false))
            }
        }
    }

}

