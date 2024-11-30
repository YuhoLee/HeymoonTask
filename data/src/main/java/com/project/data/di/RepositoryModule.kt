package com.project.data.di

import com.project.data.local.db.FavoriteCollectionDatabase
import com.project.data.remote.datasource.CollectionDataSource
import com.project.data.repository.CollectionRepositoryImpl
import com.project.domain.repository.CollectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCollectionRepository(
        collectionDataSource: CollectionDataSource,
        favoriteCollectionDatabase: FavoriteCollectionDatabase
    ): CollectionRepository =
        CollectionRepositoryImpl(
            collectionDataSource = collectionDataSource,
            favoriteCollectionDatabase = favoriteCollectionDatabase
        )

}