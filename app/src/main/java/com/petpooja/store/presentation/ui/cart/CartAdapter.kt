package com.petpooja.store.presentation.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petpooja.store.R
import com.petpooja.store.domain.entities.Product
import com.petpooja.store.presentation.commons.AppUtils
import kotlinx.android.synthetic.main.product_item.view.*
import javax.inject.Inject


class CartAdapter @Inject constructor(
        private val productList: MutableList<Product>,
        private val mContext: Context
) : RecyclerView.Adapter<CartAdapter.UserViewHolder>() {


    private val mLayoutInflater = LayoutInflater.from(mContext)
    private lateinit var callBackListener: ProductCartCallbacks

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setListener(mCallback: ProductCartCallbacks) {
        callBackListener = mCallback
    }

    fun addItems(mList: ArrayList<Product>?) {
        if (mList != null) {
            clearItems()
            productList.addAll(mList)
            notifyDataSetChanged()
        }
    }


    private fun clearItems() {
        productList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = mLayoutInflater.inflate(R.layout.product_item, parent, false)
        return UserViewHolder(view)
    }

    // stores and recycles views as they are scrolled off screen
    inner class UserViewHolder internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        var textViewTitle = itemView.textViewTitle as TextView
        var textViewDesc = itemView.textViewDesc as TextView
        var textViewPrice = itemView.textViewPrice as TextView
        var textViewCount = itemView.textViewCount as TextView
        var textViewCategory = itemView.textViewCategory as TextView
        var imageView = itemView.imageViewProduct as ImageView
        var imageViewAdd = itemView.imageViewAdd as ImageView
        var imageViewRemove = itemView.imageViewRemove as ImageView
        var imageViewDelete = itemView.imageViewDelete as ImageView
        var root = itemView.constraintRoot as ConstraintLayout
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val product = productList[position]
        holder.textViewTitle.text = product.title
        holder.textViewDesc.text = product.description
        holder.textViewPrice.text = mContext.resources.getString(R.string.postfix_price, "${product.price}")
        holder.textViewCategory.text = product.category
        holder.textViewCount.text = "${product.count}"
        holder.imageViewDelete.visibility = View.VISIBLE
        if (AppUtils.isNetworkConnected(mContext)) {
            Glide.with(mContext)
                    .load(product.image)
                    .into(holder.imageView)
        }
        holder.root.setOnClickListener {
            callBackListener.onClickItem(product)
        }
        holder.imageViewAdd.setOnClickListener {

            val count = product.count
            product.count = count.plus(1)
            holder.textViewCount.text = "${product.count}"
            notifyDataSetChanged()
            updateTotalCount()
            callBackListener.addItem(product)
        }

        holder.imageViewRemove.setOnClickListener {
            val count = product.count
            if (count > 1) {
                product.count = count.minus(1)
                holder.textViewCount.text = "${product.count}"
                notifyItemChanged(position)
                updateTotalCount()
                callBackListener.removeItem(product)
            } else {
                productList.remove(product)
                notifyDataSetChanged()
                callBackListener.onItemDelete(product)
            }

        }

        holder.imageViewDelete.setOnClickListener {
            productList.remove(product)
            notifyItemChanged(position)
            callBackListener.onItemDelete(product)
        }


    }

    private fun updateTotalCount() {
        var totalCount = 0
        productList.forEach {
            totalCount += it.count
        }
        callBackListener.onItemAction(totalCount)
    }

    interface ProductCartCallbacks {
        fun onClickItem(product: Product)
        fun onItemAction(totalCount: Int)
        fun addItem(product: Product)
        fun removeItem(product: Product)
        fun onItemDelete(product: Product)
    }

}
