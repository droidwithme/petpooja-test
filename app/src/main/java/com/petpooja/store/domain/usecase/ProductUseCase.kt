package com.petpooja.store.domain.usecase

import com.petpooja.store.domain.entities.CartProducts
import com.petpooja.store.domain.entities.Categories
import com.petpooja.store.domain.entities.Product
import com.petpooja.store.domain.repository.DomainRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductUseCase @Inject constructor(val repository: DomainRepo) {

    fun getProducts(category: String?): Flow<ArrayList<Product>?> {
        return repository.getProducts(category)
    }


    fun addProduct(product: Product): Flow<String> {
        return repository.addToCart(product)
    }

    fun removeProduct(product: Product) : Flow<String> {
        return repository.removeFromCart(product)
    }

    fun deleteProduct(product: Product) : Flow<String> {
        return repository.deleteFromDB(product)
    }

    fun deleteAll() : Flow<CartProducts?> {
        return repository.deleteAll()
    }

    fun getFromCart(): Flow<CartProducts?> {
        return repository.getAllFromCart()
    }

    fun getCategories(): Flow<Categories> {
        return repository.getCategories()
    }
}