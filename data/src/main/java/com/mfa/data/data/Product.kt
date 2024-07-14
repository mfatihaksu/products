package com.mfa.data.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Product(
    @SerialName("product_id")
    val id: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("price")
    val price: Int? = null,

    @SerialName("image")
    val image: String? = null,

    @SerialName("description")
    val description: String? = null
)

fun Product.toProductListUIObject() = ProductListUIObject(
    id = this.id,
    name = this.name,
    price = this.price,
    image = this.image
)

fun Product.toProductDetailUIObject() = ProductDetailUIObject(
    id = this.id,
    name = this.name,
    price = this.price,
    image = this.image,
    description = this.description
)

fun Product.toProductEntity() = ProductEntity(
    uId = Random.nextInt(),
    id = this.id,
    name = this.name,
    price = this.price,
    image = this.image,
    description = this.description
)

@Serializable
data class Products(val products: List<Product>)


@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val uId: Int,

    @ColumnInfo(name = "id")
    val id: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "price")
    val price: Int? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null
)

fun ProductEntity.toProductListUIObject() = ProductListUIObject(
    id = this.id,
    name = this.name,
    price = this.price,
    image = this.image
)

fun ProductEntity.toProductDetailUIObject() = ProductDetailUIObject(
    id = this.id,
    name = this.name,
    price = this.price,
    image = this.image,
    description = this.description
)

data class ProductListUIObject(
    val id : String? = null,
    val name : String? = null,
    val price : Int? = null,
    val image : String? = null
)

data class ProductDetailUIObject(
    val id : String? = null,
    val name : String? = null,
    val price : Int? = null,
    val image : String? = null,
    val description: String? = null
)
