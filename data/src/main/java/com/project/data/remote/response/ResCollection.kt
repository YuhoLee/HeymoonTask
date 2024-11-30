package com.project.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResCollection(
    @SerializedName("main_image") val mainImgUrl: String,
    @SerializedName("manage_no_year") val collectYear: String,
    @SerializedName("matrl_technic") val materialTechnic: String,
    @SerializedName("mnfct_year") val manufactureYear: String,
    @SerializedName("prdct_cl_nm") val productClass: String,
    @SerializedName("prdct_nm_eng") val productNameEn: String,
    @SerializedName("prdct_nm_korean") val productNameKo: String,
    @SerializedName("prdct_stndrd") val whSize: String,
    @SerializedName("thumb_image") val thumbImgUrl: String,
    @SerializedName("writr_nm") val maker: String
)