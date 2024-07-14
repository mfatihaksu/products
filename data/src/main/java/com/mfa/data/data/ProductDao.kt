package com.mfa.data.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getProducts(): List<Product>

    @Query("SELECT * FROM products where id=:id")
    fun getProduct(id : String) : Flow<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product : Product)

    @Query("UPDATE products SET description=:description WHERE id=:id")
    fun updateProduct(id : String, description : String? = null)
}