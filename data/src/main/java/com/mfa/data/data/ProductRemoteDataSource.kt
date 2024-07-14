package com.mfa.data.data

import com.mfa.data.helper.ApiClient
import com.mfa.data.helper.OperationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiClient: ApiClient
) : IProductRemoteDataSource {
    override val productsOperation: Flow<OperationResult<List<Product>>>
        get() = getProducts().flowOn(Dispatchers.IO)

    override fun getProducts() = apiClient.getProducts()

    override fun getProduct(id: String) = apiClient.getProductDetail(id)
}

interface IProductRemoteDataSource {
    val productsOperation: Flow<OperationResult<List<Product>>>
    fun getProducts(): Flow<OperationResult<List<Product>>>
    fun getProduct(id: String): Flow<OperationResult<Product>>
}