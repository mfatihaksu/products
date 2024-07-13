package com.mfa.data.data

import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    suspend fun getProducts(): Flow<List<ProductListUIObject>>
    suspend fun getProduct(id : String) : Flow<Product>
    fun insertProduct(product : Product)
}