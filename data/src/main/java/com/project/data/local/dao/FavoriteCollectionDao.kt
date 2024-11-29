package com.project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.data.local.entity.FavoriteCollectionEntity

@Dao
interface FavoriteCollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavoriteCollectionEntity): Long

    @Query("SELECT * FROM FavoriteCollection ORDER BY addedDatetime DESC")
    fun getFavoriteCollection(): List<FavoriteCollectionEntity>
}