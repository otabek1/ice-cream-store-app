package com.example.baza

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_buy_products.*

class BuyProductsActivity : AppCompatActivity() {

    private val TAG = "nima"
    private val db = Firebase.firestore
    private lateinit var productAdapter: ProductRecyclerAdapter
    private var productsList: List<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_products)




        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var product: Product = document.toObject<Product>()
                    productsList += product

//                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.d(TAG, "product list" + productsList.toString())
                }
                initRecyclerView()
                productAdapter.notifyDataSetChanged()
                addData()

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


    }

    fun initRecyclerView() {
        buyProductsRecycler.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductRecyclerAdapter()
        buyProductsRecycler.adapter = productAdapter


    }

    fun addData() {
        productAdapter.submitList(productsList)
        productAdapter.notifyDataSetChanged()
    }
}