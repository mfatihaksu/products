package com.mfa.data.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.channelFlow
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

    override suspend fun getProduct(id: String) = channelFlow {
        localDataSource.getProduct(id).collectLatest { productEntity: ProductEntity ->
            if (productEntity.description.isNullOrEmpty().not()){
                send(productEntity.toProductDetailUIObject())
            }else{
                remoteDataSource.getProduct(id).onSuccess { product: Product ->
                    val entity = product.toProductEntity()
                    updateProduct(id = entity.id.orEmpty(), description = entity.description)
                    trySend(product.toProductDetailUIObject())
                }.onFailure {

                }
            }
        }
    }

    override fun insertProduct(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertProduct(
                product.toProductEntity()
            )
        }
    }

    override fun updateProduct(id: String, description: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.updateProduct(id, description)
        }
    }
}