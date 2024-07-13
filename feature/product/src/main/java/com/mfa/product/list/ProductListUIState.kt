package com.mfa.product.list

import com.mfa.data.data.ProductListUIObject

data class ProductListUIState(
    val isLoading: Boolean = true,
    val products: List<ProductListUIObject>? = null,
    val errorMessage: String? = null
)