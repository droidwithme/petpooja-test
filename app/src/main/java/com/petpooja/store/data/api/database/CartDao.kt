package com.petpooja.store.data.api.database

import androidx.room.*
import com.petpooja.store.data.api.database.entity.CartEntity

@Dao
interface CartDao {

    @Query("SELECT * FROM cart")
    fun getAll(): List<CartEntity>


    @Delete
    fun delete(product: CartEntity)

    @Query("DELETE FROM cart")
    fun emptyCart()

    @Update(entity = CartEntity::class)
    fun updateProduct(vararg product: CartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertAll(list: List<CartEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insert(product: CartEntity)

}