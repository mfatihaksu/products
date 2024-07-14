package com.mfa.data.data

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductLocalDataSource @Inject constructor(
    private val db: AppDatabase
) : IProductLocalDataSource {
    override val products: Flow<List<Product>>
        get() = flow {
            emit(getProducts())
        }.flowOn(Dispatchers.IO)

    override fun getProducts() = db.productDao().getProducts()

    override fun getProduct(id: String) = db.productDao().getProduct(id)

    override fun insertProduct(product: Product) {
        db.productDao().insert(product)
    }

    override fun updateProduct(id: String, description: String?) {
        db.productDao().updateProduct(id, description)
    }
}

interface IProductLocalDataSource {
    val products: Flow<List<Product>>
    fun getProducts(): List<Product>
    fun getProduct(id: String): Flow<Product>
    fun insertProduct(product: Product)
    fun updateProduct(id : String, description : String? = null)
}