package com.mfa.data.data

import com.mfa.data.helper.OperationResult
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductLocalDataSource @Inject constructor(
    private val db: AppDatabase
) : IProductLocalDataSource {
    override val productsOperation: Flow<OperationResult<List<Product>>>
        get() = flow {
            emit(OperationResult.Success(getProducts()))
        }.flowOn(Dispatchers.IO)

    override fun getProducts() = db.productDao().getProducts()

    override fun getProduct(id: String): Flow<OperationResult<Product>> = flow {
        emit(OperationResult.Loading())
        try {
            emit(OperationResult.Success(db.productDao().getProduct(id)))
        }catch (exception : Exception){
            emit(OperationResult.Failure(exception.message.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)

    override fun insertProduct(product: Product) {
        db.productDao().insert(product)
    }

    override fun updateProduct(id: String, description: String?) {
        db.productDao().updateProduct(id, description)
    }
}

interface IProductLocalDataSource {
    val productsOperation: Flow<OperationResult<List<Product>>>
    fun getProducts(): List<Product>
    fun getProduct(id: String): Flow<OperationResult<Product>>
    fun insertProduct(product: Product)
    fun updateProduct(id : String, description : String? = null)
}