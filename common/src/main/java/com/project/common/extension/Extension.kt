package com.project.common.extension

object Extension {
    fun String?.orEmpty() = this ?: ""
    fun String?.orOneSpaceEmpty() = this ?: " "
    fun String?.orDefault(default: String = "") = this ?: default
}