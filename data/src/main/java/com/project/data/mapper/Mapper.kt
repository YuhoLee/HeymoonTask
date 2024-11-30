package com.project.data.mapper

import com.project.data.local.entity.FavoriteCollectionEntity
import com.project.data.remote.response.ResCollection
import com.project.domain.model.LocalCollectionInsertModel
import com.project.domain.model.LocalCollectionModel
import com.project.domain.model.RemoteCollectionModel

object Mapper {
    fun List<ResCollection>.toRemoteCollectionModel() =
        this.map {
            RemoteCollectionModel(
                mainImgUrl = it.mainImgUrl,
                collectYear = it.collectYear,
                materialTechnic = it.materialTechnic,
                manufactureYear = it.manufactureYear,
                productClass = it.productClass,
                productNameEn = it.productNameEn,
                productNameKo = it.productNameKo,
                whSize = it.whSize,
                thumbImgUrl = it.thumbImgUrl,
                maker = it.maker,
            )
        }

    fun List<FavoriteCollectionEntity>.toLocalCollectionModel() =
        this.map {
            LocalCollectionModel(
                id = it.id,
                productNameKo = it.productNameKo,
                productNameEn = it.productNameEn,
                maker = it.maker,
                description = it.description,
                productClass = it.productClass,
                whSize = it.whSize,
                manufactureYear = it.manufactureYear,
                collectYear = it.collectYear,
                materialTechnic = it.materialTechnic,
                thumbImgUrl = it.thumbImgUrl,
                mainImgUrl = it.mainImgUrl,
                addedDatetime = it.addedDatetime
            )
        }

    fun LocalCollectionInsertModel.toFavoriteCollectionEntity() =
        FavoriteCollectionEntity(
            productNameKo = this.productNameKo,
            productNameEn = this.productNameEn,
            maker = this.maker,
            description = this.description,
            productClass = this.productClass,
            whSize = this.whSize,
            manufactureYear = this.manufactureYear,
            collectYear = this.collectYear,
            materialTechnic = this.materialTechnic,
            thumbImgUrl = this.thumbImgUrl,
            mainImgUrl = this.mainImgUrl,
            addedDatetime = this.addedDatetime,
        )
}