package com.petpooja.store.data.api.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
        @PrimaryKey
        var id: Int,
        var title: String,
        var price: Double,
        var category: String,
        var description: String,
        var image: String,
        var count: Int = 0
)