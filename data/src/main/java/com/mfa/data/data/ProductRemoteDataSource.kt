package com.mfa.data.data

import com.mfa.data.helper.ApiClient
import com.mfa.data.helper.OperationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiClient: ApiClient
) : IProductRemoteDataSource {
    override val products: Flow<List<Product>>
        get() = channelFlow {
            getProducts().onSuccess {
                trySend(it.products)
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getProducts(): OperationResult<Products> {
        return apiClient.getProducts()
    }

    override suspend fun getProduct(id: String): OperationResult<Product> {
        return apiClient.getProductDetail(id)
    }
}

interface IProductRemoteDataSource {
    val products: Flow<List<Product>>
    suspend fun getProducts(): OperationResult<Products>
    suspend fun getProduct(id: String): OperationResult<Product>
}