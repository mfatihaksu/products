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

class ApiClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url("https://s3-eu-west-1.amazonaws.com/developer-application-test/") }

        install(Logging) {
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getProducts(): ApiOperation<Products> {
        return safeApiCall {
            client.get("cart/list").body<Products>()
        }
    }

    suspend fun getProductDetail(id: String): ApiOperation<Product> {
        return safeApiCall {
            client.get("cart/${id}/detail").body<Product>()
        }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch (e: Exception) {
            ApiOperation.Failure(exception = e)
        }
    }
}