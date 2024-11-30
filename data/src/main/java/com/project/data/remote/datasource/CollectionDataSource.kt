package com.project.data.remote.datasource

import com.project.data.BuildConfig
import com.project.data.remote.response.ResCollectionApiCall
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectionDataSource {

    @GET("{authKey}/{dataType}/SemaPsgudInfoKorInfo/" +
            "{startIdx}/{endIdx}/{productClass}/" +
            "{collectYear}/{productNameKo}/" +
            "{productNameEn}")
    suspend fun fetchCollections(
        @Path("authKey") authKey: String = BuildConfig.GALLERY_API_KEY,
        @Path("dataType") dataType: String = "json",
        @Path("startIdx") startIdx: Int,
        @Path("endIdx") endIdx: Int,
        @Path("productClass") productClass: String,
        @Path("collectYear") collectYear: String,
        @Path("productNameKo") productNameKo: String,
        @Path("productNameEn") productNameEn: String,
    ): Response<ResCollectionApiCall>
}