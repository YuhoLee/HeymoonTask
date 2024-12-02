package com.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteCollection")
data class FavoriteCollectionEntity(
    val productNameKo: String,
    val productNameEn: String,
    val maker: String,
    val productClass: String,
    val whSize: String,
    val manufactureYear: String,
    val collectYear: String,
    val materialTechnic: String,
    val thumbImgUrl: String,
    val mainImgUrl: String,
    val addedDatetime: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}