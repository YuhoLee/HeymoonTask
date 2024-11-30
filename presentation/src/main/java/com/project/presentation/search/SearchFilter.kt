package com.project.presentation.search

import androidx.annotation.StringRes
import com.project.presentation.R

data class SearchFilter(
    val productClasses: List<ProductClass>,
    val sortType: SortType
){
    companion object{
        fun create() = SearchFilter(
            productClasses = listOf(),
            sortType = SortType.ManufactureYearAsc
        )
    }
}


enum class ProductClass(val value: String, @StringRes val strId: Int? = null) {
    Painting(value = "회화", strId = R.string.text_search_filter_painting),
    KoPainting(value = "한국화", strId = R.string.text_search_filter_ko_painting),
    DrawingAndEngraving(
        value = "드로잉&판화",
        strId = R.string.text_search_filter_drawing_and_engraving
    ),
    Sculpture(value = "조각", strId = R.string.text_search_filter_sculpture),
    NewMedia(value = "뉴미디어", strId = R.string.text_search_filter_new_media),
    Photo(value = "사진", strId = R.string.text_search_filter_photo),
    Installation(value = "설치", strId = R.string.text_search_filter_installation),
    Design(value = "디자인", strId = R.string.text_search_filter_design),
}

enum class SortType{
    ManufactureYearAsc,
    ManufactureYearDesc
}