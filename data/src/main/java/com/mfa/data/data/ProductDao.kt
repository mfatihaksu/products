package com.mfa.data.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM productentity")
    fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM productentity where id=:id")
    fun getProduct(id : String) : Flow<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product : ProductEntity)

    @Query("UPDATE productentity SET description=:description WHERE id=:id")
    fun updateProduct(id : String, description : String? = null)
}