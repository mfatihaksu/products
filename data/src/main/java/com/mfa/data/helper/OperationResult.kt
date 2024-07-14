package com.mfa.data.helper

sealed class OperationResult<T>(val data : T? = null, val errorMessage : String? = null) {
    class Success<T>(data: T) : OperationResult<T>(data = data)
    class Failure<T>(exception: String) : OperationResult<T>(errorMessage = exception)
    class Loading<T>:OperationResult<T>()
}