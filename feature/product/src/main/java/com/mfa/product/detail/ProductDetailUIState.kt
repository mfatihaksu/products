package com.mfa.product.detail

import com.mfa.data.data.Product

sealed interface ProductDetailUIState {
    data object Loading : ProductDetailUIState
    data class Success(val product: Product?) : ProductDetailUIState
    data class Failure(val message: String) : ProductDetailUIState
}



