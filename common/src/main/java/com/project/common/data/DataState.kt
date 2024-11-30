package com.project.common.data

sealed class DataState<T> (val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : DataState<T>(data)
    class Error<T>(val exception: Exception, data: T? = null) : DataState<T>(data)
    class Loading<T>(val isLoading: Boolean = true) : DataState<T>(null)
}