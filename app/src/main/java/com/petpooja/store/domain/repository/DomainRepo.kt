package com.petpooja.store.domain.repository

import com.petpooja.store.domain.entities.*
import kotlinx.coroutines.flow.Flow

interface DomainRepo {
    //for product usecase
    fun getProducts(category: String?): Flow<ArrayList<Product>?>

    fun getAllFromCart(): Flow<CartProducts?>

    fun getCategories(): Flow<Categories>

    fun addToCart(product: Product): Flow<String>

    fun removeFromCart(product: Product): Flow<String>

    fun deleteFromDB(product: Product): Flow<String>

    fun deleteAll(): Flow<CartProducts?>


}