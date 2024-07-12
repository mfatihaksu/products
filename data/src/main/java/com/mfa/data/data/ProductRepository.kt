package com.mfa.data.data

import com.mfa.data.helper.ApiClient
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val client: ApiClient
) : IProductRepository {
    override suspend fun getProducts() = client.getProducts()
    override suspend fun getProduct(id: String) = client.getProductDetail(id)
}