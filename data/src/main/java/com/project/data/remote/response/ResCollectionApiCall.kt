package com.project.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResCollectionApiCall(
    @SerializedName("SemaPsgudInfoKorInfo") val res: ResCollectionValue
)