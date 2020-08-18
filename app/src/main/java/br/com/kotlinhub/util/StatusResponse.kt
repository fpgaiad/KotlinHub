package br.com.kotlinhub.util

sealed class StatusResponse<T>(
    val data: T? = null,
    val code: Int? = null
) {
    class Success<T>(data: T? = null) : StatusResponse<T>(data)
    class Error<T>(code: Int, data: T? = null) : StatusResponse<T>(data, code)
    class Loading<T> : StatusResponse<T>()
}