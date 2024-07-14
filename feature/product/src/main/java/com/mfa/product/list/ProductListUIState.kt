package com.mfa.product.list

import com.mfa.data.data.Product

sealed interface ProductListUIState {
    data object Loading : ProductListUIState
    data class Success(val products: List<Product>) : ProductListUIState
    data class Failure(val message: String) : ProductListUIState
}