package com.petpooja.store.data.repository

import android.content.Context
import androidx.room.Room
import com.petpooja.store.data.api.database.AppDatabase
import com.petpooja.store.data.api.live.APIServices
import com.petpooja.store.domain.entities.Product
import com.petpooja.store.domain.repository.DomainRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

import android.net.ConnectivityManager
import android.util.Log
import com.petpooja.store.data.api.database.entity.CartEntity
import com.petpooja.store.domain.entities.CartProducts
import com.petpooja.store.domain.entities.Categories
import java.lang.Exception


@Singleton
class DomainRepoImp @Inject constructor(
        private val APIServices: APIServices,
        private val context: Context
) : BaseRepoImp(), DomainRepo {
    var db: AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "demo_app.db").build()

    //for product
    override fun getProducts(category: String?): Flow<ArrayList<Product>?> = flow {
        Log.e("DomainRepoImp", "getProducts()")
        if (isNetworkAvailable(context)) {
            if (category == null) {
                emit(getProductsFromServer())
            } else {
                emit(getCategoryProduct(category))
            }
        }
    }

    override fun getAllFromCart(): Flow<CartProducts> = flow {
        Log.e("DomainRepoImp", "getAllFromCart()")
        emit(CartProducts(getProductList()))
    }

    override fun getCategories(): Flow<Categories> = flow {
        Log.e("DomainRepoImp", "getCategories()")
        emit(Categories(safeApiCall(
                { APIServices.getCategory() },
                "getCategories"
        ) as ArrayList<String>))

    }

    override fun addToCart(product: Product): Flow<String> = flow {
        Log.e("DomainRepoImp", "addToCart()")
        try {
            db.cartDao().insert(Product.toCart(product))
            emit("Added to cart")
        } catch (e: Exception) {
            emit(e.localizedMessage)
        }
    }

    override fun removeFromCart(product: Product): Flow<String> = flow {
        Log.e("DomainRepoImp", "removeFromCart()")
        try {
            if (product.count == 0) {
                db.cartDao().delete(Product.toCart(product))
            } else {
                db.cartDao().insert(Product.toCart(product))
            }

            emit("Removed from cart")
        } catch (e: Exception) {
            emit(e.localizedMessage)
        }
    }

    override fun deleteFromDB(product: Product): Flow<String> = flow {
        try {
            db.cartDao().delete(Product.toCart(product))
            emit("Removed from cart")
        } catch (e: Exception) {
            emit(e.localizedMessage)
        }
    }

    override fun deleteAll(): Flow<CartProducts?> = flow {
        Log.e("DomainRepoImp", "deleteAll()")
        db.cartDao().emptyCart()
        emit(CartProducts(getProductList()))
    }


    private suspend fun getProductsFromServer(): ArrayList<Product> {
        Log.e("DomainRepoImp", "getProductsFromServer()")
        return safeApiCall(
                { APIServices.getProducts() },
                "getProductsFromServer"
        ) as ArrayList<Product>

    }

    private suspend fun getCategoryProduct(category: String?): ArrayList<Product> {
        Log.e("DomainRepoImp", "getCategoryProduct()")
        return safeApiCall(
                { APIServices.getCategoryProducts(category!!) },
                "getCategoryProduct"
        ) as ArrayList<Product>

    }

    private fun getProductList(): ArrayList<Product> {
        val productEntityList: ArrayList<CartEntity> =
                db.cartDao().getAll() as ArrayList<CartEntity>
        Log.e("DomainImpl", "getProductList($productEntityList)")
        return productEntityList.map {
            Product(
                    it.id,
                    it.title,
                    it.price,
                    it.category,
                    it.description,
                    it.image,
                    it.count,
            )
        } as ArrayList<Product>
    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}