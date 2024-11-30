package com.project.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResCollectionValue(
    @SerializedName("RESULT") val resCollectionApiCallState: ResCollectionApiCallState,
    @SerializedName("list_total_count") val totalCnt: Int,
    @SerializedName("row") val collectionList: List<ResCollection>
)