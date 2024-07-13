package com.mfa.data.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM productentity")
    fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM productentity where uid=:id")
    fun getProduct(id : Int) : Flow<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product : ProductEntity)
}