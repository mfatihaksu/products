package com.mfa.data.helper

import com.mfa.data.data.Product
import com.mfa.data.data.Products
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class ApiClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url("https://s3-eu-west-1.amazonaws.com/developer-application-test/") }

        install(Logging) {
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    fun getProducts(): Flow<OperationResult<List<Product>>> = flow {
        emit(OperationResult.Loading())
        try {
            emit(OperationResult.Success(client.get("cart/list").body<Products>().products))
        } catch (exception: Exception) {
            emit(OperationResult.Failure(exception.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)

    fun getProductDetail(id: String): Flow<OperationResult<Product>> = flow {
        emit(OperationResult.Loading())
        try {
            emit(OperationResult.Success(client.get("cart/${id}/detail").body<Product>()))
        } catch (exception: Exception) {
            emit(OperationResult.Failure(exception.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}