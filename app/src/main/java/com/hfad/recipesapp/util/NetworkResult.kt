package com.hfad.recipesapp.util

import android.os.Message

sealed class NetworkResult<out T: Any> {
    data class Success<out T: Any>(val data: T): NetworkResult<T>()
    data class Error(val exception: String): NetworkResult<Nothing>()
    object Loading: NetworkResult<Nothing>()
}
