package com.mfa.data.data

import com.mfa.data.helper.ApiClient
import com.mfa.data.helper.ApiOperation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiClient: ApiClient
) : IProductRemoteDataSource {
    override val products: Flow<List<Product>>
        get() = channelFlow {
            getProducts()
                .onSuccess { products ->
                    CoroutineScope(Dispatchers.IO).launch {
                        send(products.products)
                    }
                }.onFailure {

                }
        }.flowOn(Dispatchers.IO)

    override suspend fun getProducts(): ApiOperation<Products> {
        return apiClient.getProducts()
    }

    override suspend fun getProduct(id: String): ApiOperation<Product> {
        return apiClient.getProductDetail(id)
    }
}

interface IProductRemoteDataSource {
    val products: Flow<List<Product>>
    suspend fun getProducts(): ApiOperation<Products>
    suspend fun getProduct(id: String): ApiOperation<Product>
}