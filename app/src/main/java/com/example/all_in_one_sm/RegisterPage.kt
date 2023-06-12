package com.example.all_in_one_sm
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterPage : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var country: EditText
    private lateinit var city: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var loginAccount: TextView

    private fun performLogin(name: String, username: String, email: String, phone: Int,
    country: String, city: String, password: String, confirmPassword: String): Boolean {
        // Your login logic here
        // Return true if login is successful, false otherwise
        return true
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        name = findViewById(R.id.nameEditText)
        username = findViewById(R.id.usernameEditText)
        email = findViewById(R.id.emailEditText)
        phone = findViewById(R.id.phoneEditText)
        country = findViewById(R.id.countryEditText)
        city = findViewById(R.id.cityEditText)
        password = findViewById(R.id.passwordEditText)
        confirmPassword = findViewById(R.id.confirmPasswordEditText)
        registerButton = findViewById(R.id.registerButton)
        loginAccount = findViewById(R.id.loginAccountInput)

        registerButton.setOnClickListener {
            // Perform registration logic here
            val isLoggedIn = performLogin(name.text.toString() ,username.text.toString(),
                email.text.toString(), phone.inputType, country.text.toString(),
                city.text.toString(), password.text.toString(), confirmPassword.text.toString())

            if (isLoggedIn) {
                navigateToLogin()
            } else {
                // Show error message or perform other actions for failed login
            }
        }

        loginAccount.setOnClickListener {
            navigateToLogin()
        }
    }
}

