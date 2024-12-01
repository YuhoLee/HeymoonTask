package com.project.domain.usecase

import com.project.domain.repository.CollectionRepository
import javax.inject.Inject

class FetchSearchCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    suspend operator fun invoke(
        startIdx: Int,
        endIdx: Int,
        productClass: String? = null,
        collectYear: String? = null,
        productNameKo: String? = null,
        productNameEn: String? = null
    ) = collectionRepository.fetchRemoteCollections(
        startIdx = startIdx,
        endIdx = endIdx,
        productClass = productClass,
        collectYear = collectYear,
        productNameKo = productNameKo,
        productNameEn = productNameEn,
    )
}