package com.project.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResCollectionApiCallState(
    @SerializedName("CODE") val code: String,
    @SerializedName("MESSAGE") val msg: String
)