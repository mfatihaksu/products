package com.mfa.data.data

import com.mfa.data.helper.ApiClient
import com.mfa.data.helper.ApiOperation
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiClient: ApiClient
) : IProductRemoteDataSource {

    override suspend fun getProducts(): ApiOperation<Products> {
        return apiClient.getProducts()
    }

    override suspend fun getProduct(id: String): ApiOperation<Product> {
        return apiClient.getProductDetail(id)
    }
}

interface IProductRemoteDataSource {
    suspend fun getProducts(): ApiOperation<Products>
    suspend fun getProduct(id: String): ApiOperation<Product>
}