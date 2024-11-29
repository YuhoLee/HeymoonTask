package com.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteCollection")
data class FavoriteCollectionEntity(
    val productNameKo: String,
    val productNameEn: String,
    val writer: String,
    val description: String,
    val className: String,
    val whSize: String,
    val makeYear: Int,
    val collectYear: Int,
    val technic: String,
    val thumbImg: String,
    val mainImg: String,
    val addedDatetime: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}