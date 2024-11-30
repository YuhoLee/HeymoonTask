package com.project.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.project.common.extension.Extension.orDefault
import com.project.common.extension.Extension.orOneSpaceEmpty
import com.project.data.mapper.Mapper.toRemoteCollectionModel
import com.project.data.remote.NetworkUtils
import com.project.data.remote.datasource.CollectionDataSource
import com.project.domain.model.RemoteCollectionModel

class CollectionPagingSource(
    private val collectionDataSource: CollectionDataSource,
    private val productClass: String?,
    private val collectYear: String?,
    private val productNameKo: String?,
    private val productNameEn: String?
) : PagingSource<Int, RemoteCollectionModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RemoteCollectionModel> {
        val currentPage = params.key ?: 0 // 초기 페이지 키는 0
        return try {
            val response = NetworkUtils.safeApiCall {
                collectionDataSource.fetchCollections(
                    startIdx = currentPage * params.loadSize,
                    endIdx = (currentPage + 1) * params.loadSize - 1,
                    productClass = productClass.orOneSpaceEmpty(),
                    collectYear = collectYear.orOneSpaceEmpty(),
                    productNameKo = productNameKo.orOneSpaceEmpty(),
                    productNameEn = productNameEn.orOneSpaceEmpty(),
                )
            }

            val list = response?.res?.collectionList?.toRemoteCollectionModel() ?: listOf()

            LoadResult.Page(
                data = list, // 현재 페이지 데이터
                prevKey = if (currentPage == 0) null else currentPage - 1, // 이전 페이지 키
                nextKey = if (list.isEmpty()) null else currentPage + 1 // 다음 페이지 키
            )
        } catch (e: Exception) {
            LoadResult.Error(e) // 에러 처리
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RemoteCollectionModel>): Int? {
        // 새로고침 시 사용할 키 반환
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}