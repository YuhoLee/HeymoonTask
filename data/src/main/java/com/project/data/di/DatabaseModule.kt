package com.project.data.di

import android.app.Application
import androidx.room.Room
import com.project.data.local.dao.FavoriteCollectionDao
import com.project.data.local.db.FavoriteCollectionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    internal fun provideFavoriteCollectionDatabase(application: Application): FavoriteCollectionDatabase =
        Room.databaseBuilder(
            context = application.applicationContext,
            klass = FavoriteCollectionDatabase::class.java,
            name = FavoriteCollectionDatabase.FAVORITE_COLLECTION_DATABASE
        ).build()

    @Provides
    @Singleton
    internal fun provideFavoriteCollectionDao(favoriteCollectionDatabase: FavoriteCollectionDatabase): FavoriteCollectionDao =
        favoriteCollectionDatabase.getFavoriteCollectionDao()
}
