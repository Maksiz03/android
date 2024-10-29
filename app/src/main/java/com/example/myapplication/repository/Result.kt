package com.example.myapplication.repository

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {
        fun <T> success(value: T) = Success(value)
        fun error(exception: Exception) = Error(exception)
    }
}
