package com.mfa.data.helper

sealed interface OperationResult<T> {
    data class Success<T>(val data: T) : OperationResult<T>
    data class Failure<T>(val exception: Exception) : OperationResult<T>

    fun <R> mapSuccess(transform: (T) -> R): OperationResult<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(exception)
        }
    }

    suspend fun onSuccess(block: (T) -> Unit): OperationResult<T> {
        if (this is Success) block(data)
        return this
    }

    suspend fun onFailure(block: (Exception) -> Unit): OperationResult<T> {
        if (this is Failure) block(exception)
        return this
    }
}