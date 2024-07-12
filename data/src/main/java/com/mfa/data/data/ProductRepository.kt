package com.mfa.data.data

import com.mfa.data.helper.ApiClient
import com.mfa.data.helper.ApiOperation
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val client : ApiClient
): IProductRepository {
    override suspend fun getProducts(): ApiOperation<Products> {
        return client.getProducts()
    }

    override suspend fun getProduct(id: String): Flow<Product> {
        return flow {
            client.getProductDetail(id)
        }
    }
}