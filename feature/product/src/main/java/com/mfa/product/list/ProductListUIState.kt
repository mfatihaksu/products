package com.mfa.product.list

import com.mfa.data.data.Product
import com.mfa.ui.BaseUIState

data class ProductListUIState(var products: List<Product>? = null): BaseUIState()