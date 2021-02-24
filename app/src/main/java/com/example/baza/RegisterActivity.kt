package com.example.baza

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    val TAG: String = "nima"
    var category: String = ""
    val user = hashMapOf(
        "userId" to "",
        "email" to "",
        "category" to ""
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
//        category = getIntent().getStringExtra("category").toString()

        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {
            signInUser()
        }


        registerBtn.setOnClickListener {
            signUpUser()
        }
//        Log.d(TAG, "onCreate: ")

        base_constraint.setOnClickListener {
            hideKeyboard(base_constraint)
        }
    }

    private fun checkInput() {
        if (emailEditText.text.toString().isEmpty()) {
            emailEditText.error = getString(R.string.email_kirit)
            emailEditText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()) {
            emailEditText.error = getString(R.string.togri_email_kirit)
            emailEditText.requestFocus()
            return
        }

        if (passwordEditText.text.toString().isEmpty()) {
            passwordEditText.error = getString(R.string.parol_kirit)
            passwordEditText.requestFocus()
            return
        }
    }

    private fun signUpUser() {

        checkInput()

        auth.createUserWithEmailAndPassword(
            emailEditText.text.toString(),
            passwordEditText.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val current_user = auth.currentUser
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    user["userId"] = current_user?.uid.toString();
                    user["email"] = current_user?.email.toString()
                    user["category"] = category.toString()
                    db.collection("users").add(user)
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)

//                        updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed. " + task.exception?.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
//                        updateUI(null)
                }

                // ...
            }

    }

    private fun signInUser() {
        checkInput()
        auth.signInWithEmailAndPassword(
            emailEditText.text.toString(),
            passwordEditText.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                    // ...
                }

                // ...
            }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}