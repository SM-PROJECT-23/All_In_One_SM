package com.example.all_in_one_sm
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginPage : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var forgotPassword: TextView
    private lateinit var register: TextView
    private lateinit var login: Button

    private fun performLogin(username: String, password: String): Boolean {
        // Your login logic here
        // Return true if login is successful, false otherwise
        return true
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity to prevent navigating back to the login page
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterPage::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Initialize views
        username = findViewById(R.id.usernameEditText)
        password = findViewById(R.id.passwordEditText)
        forgotPassword = findViewById(R.id.forgot_password_text)
        register = findViewById(R.id.register_text)
        login = findViewById(R.id.login_button)

        // Set click listeners
        forgotPassword.setOnClickListener {
            // Handle "Forgot password" click
            // Implement your logic here
        }

        login.setOnClickListener {
            // Perform login action
            val isLoggedIn = performLogin(username.text.toString(), password.text.toString())

            if (isLoggedIn) {
                navigateToHome()
            }
        }

        register.setOnClickListener {
            navigateToRegister()
        }
    }
}