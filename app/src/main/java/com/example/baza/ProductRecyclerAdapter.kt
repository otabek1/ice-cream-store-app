package com.example.baza

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_product_item.view.*

private const val TAG = "nima"

class ProductRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onBindViewHolder: ")

        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_product_item, parent, false)
        );
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(items.get(position))
                Log.d(TAG, "onBindViewHolder: ")
            }
        }
    }

    fun submitList(productList: List<Product>) {
        items = productList
    }


    class ProductViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val productName = itemView.productNameText
        val productPrice = itemView.productPriceText

        fun bind(product: Product) {
            productName.setText(product.name)
            productPrice.setText(product.price.toString())
        }
    }

}