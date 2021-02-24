package com.example.baza

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_category_select.*


class CategorySelectActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val TAG: String = "nima"
    val db = Firebase.firestore
    val names = arrayListOf<String>()
    lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_select)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
        db.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    names.add(document["name"] as String)

                    Log.d(TAG, "sdas " + document["name"])
                }
                createRadioButton()
                setSelectBtn()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


    }

    private fun setSelectBtn() {

        selectCategoryBtn.setOnClickListener {

            if (radioGroup1.checkedRadioButtonId == -1) {
                // no radio buttons are checked
                Toast.makeText(
                    baseContext,
                    getString(R.string.kategoriyani_tanlang),
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                // one of the radio buttons is checked
                val selectedOption: Int = radioGroup1!!.checkedRadioButtonId

                // Assigning id of the checked radio button
                radioButton = findViewById(selectedOption)

                val intent = Intent(this, RegisterActivity::class.java)
                intent.putExtra("category", radioButton.text)
                startActivity(intent)
                finish()

                // Displaying text of the checked radio button in the form of toast
                Toast.makeText(baseContext, radioButton.text, Toast.LENGTH_SHORT).show()
            }


        }
    }


    private fun createRadioButton() {
        val rb = arrayOfNulls<RadioButton>(names.size)

        for (i in 0..rb.size - 1) {
            rb[i] = RadioButton(this)
            rb[i]?.setTextSize(18f)
            radioGroup1.addView(rb[i]) //the RadioButtons are added to the radioGroup instead of the layout
            rb[i]!!.text = names[i]
        }

    }
}