package com.example.hkdimsum

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
    }

    fun register(view: View) {

        val editTextEmailAddress =
            findViewById<TextView>(R.id.editTextEmailAddress)
        val editTextPassword =
            findViewById<TextView>(R.id.editTextPassword)
        val email = editTextEmailAddress.text.toString()
        val password = editTextPassword.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(
                    this,
                    user!!.email + " Hello, Register successful",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    fun goToLogin(view: View) {

        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)

    }
}