package com.project.presentation.detail.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionDetailItem(
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
) : Parcelable
