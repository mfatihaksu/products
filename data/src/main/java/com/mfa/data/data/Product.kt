package com.mfa.data.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Entity(tableName = "products")
data class Product(

    @PrimaryKey(autoGenerate = true)
    @Transient
    val uId: Int? = null,

    @SerialName("product_id")
    val id: String? = null,

    @SerialName("name")
    @ColumnInfo(name = "name")
    val name: String? = null,

    @SerialName("price")
    @ColumnInfo(name = "price")
    val price: Int? = null,

    @SerialName("image")
    @ColumnInfo(name = "image")
    val image: String? = null,

    @SerialName("description")
    @ColumnInfo(name = "description")
    val description: String? = null
)

@Serializable
data class Products(val products: List<Product>)



