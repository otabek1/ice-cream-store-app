package com.example.baza

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var TAG: String = "nima"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        registerBtn.setOnClickListener {
            signUpUser()
        }
        Log.d(TAG, "onCreate: ")

        base_constraint.setOnClickListener {
            hideKeyboard(base_constraint)
        }
    }

    private fun signUpUser() {
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

        auth.createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
//                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed. "+ task.exception?.message.toString(),
                                Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                    }

                    // ...
                }

    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}