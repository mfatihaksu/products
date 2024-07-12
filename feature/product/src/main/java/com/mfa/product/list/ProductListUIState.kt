package com.mfa.product.list

import com.mfa.data.data.Product

data class ProductListUIState(val isLoading : Boolean = true, val products : List<Product>?= null, val errorMessage: String? = null)