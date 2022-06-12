package com.example.hkdimsum

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executor

// Code from Shape lab
class LoginActivity : AppCompatActivity() {

    // For Firebase Authtacation
    private lateinit var auth: FirebaseAuth

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var sharedPreferences: SharedPreferences? = null

    companion object {
        const val LOGINEMAIL_KEY = "LOGINEMAIL_KEY"
        const val PASSWORD_KEY = "PASSWORD_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("MySharedPreMain", MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)

                    val email: String?
                    val password: String?
                    sharedPreferences?.getString(LOGINEMAIL_KEY, "").let {
                        email = it
                    }
                    sharedPreferences?.getString(PASSWORD_KEY, "").let {
                        password = it
                    }
                    performLogin(email ?: "", password ?: "")

                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        val biometricLoginButton =
            findViewById<Button>(R.id.biometric_login)
        biometricLoginButton.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    override fun onResume() {
        super.onResume()
        val biometricStatusTextView =
            findViewById<TextView>(R.id.biometric_status)
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                biometricStatusTextView.text = "App can authenticate using biometrics."
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                biometricStatusTextView.text = "No biometric features available on this device."
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                biometricStatusTextView.text = "Biometric features are currently unavailable."
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                // Prompts the user to create credentials that your app accepts.
                biometricStatusTextView.text = "Biometric features are not enrolled."
        }
    }

    fun login(view: View) {

        val editTextEmailAddress =
            findViewById<TextView>(R.id.editTextEmailAddress)
        val editTextPassword =
            findViewById<TextView>(R.id.editTextPassword)
        val email = editTextEmailAddress.text.toString()
        val password = editTextPassword.text.toString()
        performLogin(email, password)
    }

    private fun performLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val editor = sharedPreferences!!.edit()
                editor.putString(LOGINEMAIL_KEY, email)
                editor.putString(PASSWORD_KEY, password)
                editor.commit()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->

            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()

        }
    }

    fun goToRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}