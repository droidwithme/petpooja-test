package com.petpooja.store.data.api.live

import com.petpooja.store.domain.entities.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton


@Singleton
interface APIServices {

    companion object {
        const val GET_PRODUCTS: String = ("products")
        const val GET_CATEGORY: String = ("products/categories")
        const val GET_CATEGORY_PRODUCT: String = ("products/category")

    }

    @GET(GET_PRODUCTS)
    suspend fun getProducts(): Response<List<Product>>

    @GET(GET_CATEGORY_PRODUCT+"/{category}")
    suspend fun getCategoryProducts(@Path(value = "category") category: String): Response<List<Product>>

    @GET(GET_CATEGORY)
    suspend fun getCategory(): Response<ArrayList<String>>

}