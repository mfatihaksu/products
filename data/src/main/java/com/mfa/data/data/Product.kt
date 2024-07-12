package com.mfa.data.data

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val product_id: String? = null,
    val name: String? = null,
    val price: Int? = null,
    val image: String? = null
)

@Serializable
data class Products(val products : List<Product>)
