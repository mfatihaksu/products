package com.mfa.data.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val localDataSource: ProductLocalDataSource,
    private val remoteDataSource: ProductRemoteDataSource
) : IProductRepository {
    override suspend fun getProducts() = flow {
        val mProducts = mutableListOf<ProductListUIObject>()
        localDataSource.products.collectLatest { productEntities ->
            if (productEntities.isEmpty()) {
                remoteDataSource.getProducts()
                    .onSuccess { products: Products ->
                        if (products.products.isNotEmpty()) {
                            products.products.forEach { product: Product ->
                                insertProduct(product)
                                mProducts.add(product.toProductListUIObject())
                            }
                        }
                    }
                    .onFailure {

                    }
            } else {
                productEntities.forEach { productEntity ->
                    mProducts.add(productEntity.toProductListUIObject())
                }
            }
        }
        emit(mProducts)
    }

    override suspend fun getProduct(id: String): Flow<Product> {
        TODO("Not yet implemented")
    }

    override fun insertProduct(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertProduct(
                product.toProductEntity()
            )
        }
    }
}