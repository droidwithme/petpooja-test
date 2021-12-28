package com.petpooja.store.data.api.database

import androidx.room.*
import com.petpooja.store.data.api.database.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM PRODUCTS")
    fun getAll(): List<ProductEntity>

    @Insert
    fun insert(vararg product: ProductEntity)

    @Delete
    fun delete(product: ProductEntity)

    @Update(entity = ProductEntity::class)
    fun updateProduct(vararg product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertAll(list: List<ProductEntity>)

}