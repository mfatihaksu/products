package com.mfa.data.data

import com.mfa.data.helper.OperationResult
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    suspend fun getProducts(): Flow<OperationResult<List<Product>>>
    suspend fun getProduct(id : String) : Flow<OperationResult<Product>>
    fun insertProduct(product : Product)
    fun updateProduct(id : String, description : String? = null)
}