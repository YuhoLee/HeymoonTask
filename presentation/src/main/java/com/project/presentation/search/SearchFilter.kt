package com.project.presentation.search

import androidx.annotation.StringRes
import com.project.presentation.R

enum class ProductClass(val value: String, @StringRes val strId: Int) {
    Painting(value = "회화", strId = R.string.text_search_class_painting),
    KoPainting(value = "한국화", strId = R.string.text_search_class_ko_painting),
    DrawingAndEngraving(
        value = "드로잉&판화",
        strId = R.string.text_search_class_drawing_and_engraving
    ),
    Sculpture(value = "조각", strId = R.string.text_search_class_sculpture),
    NewMedia(value = "뉴미디어", strId = R.string.text_search_class_new_media),
    Photo(value = "사진", strId = R.string.text_search_class_photo),
    Installation(value = "설치", strId = R.string.text_search_class_installation),
    Design(value = "디자인", strId = R.string.text_search_class_design),
}

enum class SortType(@StringRes val strId: Int){
    ManufactureYearAsc(strId = R.string.text_search_order_by_product_year_asc),
    ManufactureYearDesc(strId = R.string.text_search_order_by_product_year_desc)
}