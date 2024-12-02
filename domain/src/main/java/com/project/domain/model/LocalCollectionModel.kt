package com.project.domain.model

data class LocalCollectionModel(
    val id: Int?,
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
    val addedDatetime: String,
)