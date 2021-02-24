package com.example.baza

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val TAG: String = "nima"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            Log.d(TAG, "signed in: " + auth.currentUser!!.email.toString())
        } else {
            Log.d(TAG, "not signed in")
        }

        buy_btn.setOnClickListener {
            val buyIntent = Intent(this, BuyProductsActivity::class.java)
            startActivity(buyIntent)
            finish()
        }
    }
}