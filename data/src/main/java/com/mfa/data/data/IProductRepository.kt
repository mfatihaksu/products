package com.mfa.data.data

import com.mfa.data.helper.ApiOperation
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    suspend fun getProducts() : ApiOperation<Products>
    suspend fun getProduct(id : String) : Flow<Product>
}