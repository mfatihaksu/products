package com.mfa.data.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Product(
    @SerialName("product_id")
    val id: String? = null,
    val name: String? = null,
    val price: Int? = null,
    val image: String? = null,
    val description : String? = null
)

@Serializable
data class Products(val products : List<Product>)
