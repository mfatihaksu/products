package com.mfa.product.detail

import com.mfa.data.data.Product
import com.mfa.ui.BaseUIState

data class ProductDetailUIState(var product : Product? = null) : BaseUIState()