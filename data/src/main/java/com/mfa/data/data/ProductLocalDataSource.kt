package com.mfa.data.data

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductLocalDataSource @Inject constructor(
    private val db: AppDatabase
) : IProductLocalDataSource {
    override val products: Flow<List<ProductEntity>>
        get() = flow {
            emit(getProducts())
        }.flowOn(Dispatchers.IO)

    override fun getProducts() = db.productDao().getProducts()

    override fun getProduct(id: Int) = db.productDao().getProduct(id)

    override fun insertProduct(product: ProductEntity) {
        db.productDao().insert(product)
    }
}

interface IProductLocalDataSource {
    val products: Flow<List<ProductEntity>>
    fun getProducts(): List<ProductEntity>
    fun getProduct(id: Int): Flow<ProductEntity>
    fun insertProduct(product: ProductEntity)
}