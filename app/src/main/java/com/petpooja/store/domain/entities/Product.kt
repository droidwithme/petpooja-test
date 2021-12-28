package com.petpooja.store.domain.entities

import com.petpooja.store.data.api.database.entity.CartEntity
import com.petpooja.store.data.api.database.entity.ProductEntity


data class Product(
    var id: Int,
    var title: String,
    var price: Double,
    var category: String,
    var description: String,
    var image: String,
    var count: Int = 0
) {
    companion object {
        fun toProduct(product: Product): ProductEntity {
            return ProductEntity(
                product.id,
                product.title,
                product.price,
                product.category,
                product.description,
                product.image,
                product.count
            )
        }
        fun toCart(product: Product): CartEntity {
            return CartEntity(
                    product.id,
                    product.title,
                    product.price,
                    product.category,
                    product.description,
                    product.image,
                    product.count
            )
        }
    }
}