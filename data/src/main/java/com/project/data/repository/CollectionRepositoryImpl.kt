package com.project.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.project.common.data.DataState
import com.project.data.local.db.FavoriteCollectionDatabase
import com.project.data.mapper.Mapper.toFavoriteCollectionEntity
import com.project.data.mapper.Mapper.toLocalCollectionModel
import com.project.data.paging.CollectionPagingSource
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
    companion object{
        const val PAGE_SIZE = 20
    }

    /** Remote */

    override suspend fun fetchRemoteCollections(
        productClass: String?,
        collectYear: String?,
        productNameKo: String?,
        productNameEn: String?
    ): Flow<PagingData<RemoteCollectionModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                CollectionPagingSource(
                    collectionDataSource = collectionDataSource,
                    productClass = productClass,
                    collectYear = collectYear,
                    productNameKo = productNameKo,
                    productNameEn = productNameEn
                )
            }
        ).flow
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

