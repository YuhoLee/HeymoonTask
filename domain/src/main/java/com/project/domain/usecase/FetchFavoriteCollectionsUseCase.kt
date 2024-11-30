package com.project.domain.usecase

import com.project.domain.repository.CollectionRepository
import javax.inject.Inject

class FetchFavoriteCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    suspend operator fun invoke() = collectionRepository.fetchLocalCollections()
}