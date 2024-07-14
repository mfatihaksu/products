package com.mfa.data.data

import com.mfa.data.helper.OperationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val localDataSource: ProductLocalDataSource,
    private val remoteDataSource: ProductRemoteDataSource
) : IProductRepository {
    override suspend fun getProducts(): Flow<OperationResult<List<Product>>> {
        localDataSource.productsOperation.collectLatest { localProducts ->
            if (localProducts is OperationResult.Success) {
                localProducts.data?.let { products: List<Product> ->
                    if (products.isEmpty()) {
                        remoteDataSource.productsOperation.collectLatest { remoteProductOperation ->
                            if (remoteProductOperation is OperationResult.Success) {
                                remoteProductOperation.data?.let { remoteProducts ->
                                    if (remoteProducts.isNotEmpty()) {
                                        remoteProducts.forEach {
                                            insertProduct(it)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return merge(localDataSource.productsOperation, remoteDataSource.productsOperation)
    }

    override suspend fun getProduct(id: String): Flow<OperationResult<Product>> {
        localDataSource.getProduct(id).collectLatest { localOperation ->
            if (localOperation is OperationResult.Success) {
                localOperation.data?.let { localProduct ->
                    if (localProduct.description.isNullOrEmpty().not()) {
                        remoteDataSource.getProduct(id).collectLatest { remoteOperation ->
                            if (remoteOperation is OperationResult.Success) {
                                remoteOperation.data?.let { remoteProduct ->
                                    updateProduct(
                                        remoteProduct.id.orEmpty(),
                                        remoteProduct.description
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        return merge(localDataSource.getProduct(id), remoteDataSource.getProduct(id))
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