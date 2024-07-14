package com.mfa.product.detail

import com.mfa.data.data.ProductDetailUIObject
import com.mfa.ui.BaseUIState

data class ProductDetailUIState(var product: ProductDetailUIObject? = null) : BaseUIState()