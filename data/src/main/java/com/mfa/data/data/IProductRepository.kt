package com.mfa.data.data

import com.mfa.data.helper.ApiOperation

interface IProductRepository {
    suspend fun getProducts() : ApiOperation<Products>
    suspend fun getProduct(id : String) : ApiOperation<Product>
}