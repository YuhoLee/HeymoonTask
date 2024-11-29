package com.project.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.data.local.dao.FavoriteCollectionDao
import com.project.data.local.entity.FavoriteCollectionEntity

@Database(entities = [FavoriteCollectionEntity::class], version = 1)
abstract class FavoriteCollectionDatabase : RoomDatabase() {
    abstract fun getFavoriteCollectionDao(): FavoriteCollectionDao

    companion object {
        const val FAVORITE_COLLECTION_DATABASE = "favorite-collection-db"
    }
}