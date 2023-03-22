package com.jt17.neofilms.models

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {

    val status: Status
        get() = when (this) {
            is Success -> Status.SUCCESS
            is Loading -> Status.LOADING
            is Error -> Status.ERROR
        }

    enum class Status {
        SUCCESS,
        LOADING,
        ERROR
    }

    class Success<T>(data: T) : Resource<T>(data, null)
    class Loading<T>(data: T? = null) : Resource<T>(data, null)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

