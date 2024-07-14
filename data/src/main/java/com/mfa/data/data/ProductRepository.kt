package com.mfa.data.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val localDataSource: ProductLocalDataSource,
    private val remoteDataSource: ProductRemoteDataSource
) : IProductRepository {
    override suspend fun getProducts() : Flow<List<Product>> {
        localDataSource.products.collectLatest { localProducts ->
            if (localProducts.isEmpty()) {
                remoteDataSource.products.collectLatest { remoteProducts ->
                    if (remoteProducts.isNotEmpty()) {
                        remoteProducts.forEach {
                            insertProduct(it)
                        }
                    }
                }
            }
        }
        return merge(localDataSource.products, remoteDataSource.products)
    }

    override suspend fun getProduct(id: String) = channelFlow {
        localDataSource.getProduct(id).collectLatest { productEntity: Product ->
            if (productEntity.description.isNullOrEmpty().not()){
                send(productEntity)
            }else{
                remoteDataSource.getProduct(id).onSuccess { product: Product ->
                    updateProduct(id = product.id.orEmpty(), description = product.description)
                    trySend(product)
                }.onFailure { exception ->
                    exception.printStackTrace()
                }
            }
        }
    }

    override fun insertProduct(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertProduct(product)
        }
    }

    override fun updateProduct(id: String, description: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.updateProduct(id, description)
        }
    }
}