package com.project.domain.usecase

import com.project.domain.model.LocalCollectionInsertModel
import com.project.domain.repository.CollectionRepository
import javax.inject.Inject

class InsertFavoriteCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    suspend operator fun invoke(model: LocalCollectionInsertModel) =
        collectionRepository.insertLocalFavoriteCollection(model = model)
}