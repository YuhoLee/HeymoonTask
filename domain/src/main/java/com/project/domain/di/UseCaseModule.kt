package com.project.domain.di

import com.project.domain.repository.CollectionRepository
import com.project.domain.usecase.FetchFavoriteCollectionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    @Singleton
    fun provideFetchFavoriteCollectionsUseCase(collectionRepository: CollectionRepository) =
        FetchFavoriteCollectionsUseCase(collectionRepository = collectionRepository)
}