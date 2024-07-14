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

    suspend fun getProducts(): OperationResult<Products> {
        return safeApiCall {
            client.get("cart/list").body<Products>()
        }
    }

    suspend fun getProductDetail(id: String): OperationResult<Product> {
        return safeApiCall {
            client.get("cart/${id}/detail").body<Product>()
        }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): OperationResult<T> {
        return try {
            OperationResult.Success(data = apiCall())
        } catch (e: Exception) {
            OperationResult.Failure(exception = e)
        }
    }
}