package com.example.all_in_one_sm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class ForgotPassword : AppCompatActivity() {

    private lateinit var OldPass : TextInputEditText
    private lateinit var NewPass : TextInputEditText
    private lateinit var forgot : Button
    private lateinit var confPass: TextInputEditText

    private fun navigateToLogin() {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

    fun updatePassword(email: String, newPassword: String): Boolean {

        val passwordStorage = mutableMapOf(
            "example@example.com" to "oldpassword"
            // Add more entries as needed
        )

        // Check if the email exists in the storage
        if (passwordStorage.containsKey(email)) {
            // Update the password
            passwordStorage[email] = newPassword
            return true // Password updated successfully
        }

        return false // Email not found or password not updated
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = Html.fromHtml("<font color=\"white\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.forgotpassword)

        val scanner = Scanner(System.`in`)

        println("Forgot Password Page")
        print("Enter your email: ")
        val email = scanner.nextLine()

        print("Enter your new password: ")
        val newPassword = scanner.nextLine()

        val passwordUpdated = updatePassword(email, newPassword)

        if (passwordUpdated) {
            println("Password updated successfully!")
        } else {
            println("Failed to update the password. Email not found.")
        }

        // Initialize views
        OldPass = findViewById(R.id.usernameEditText)
        NewPass = findViewById(R.id.passwordNew)
        confPass = findViewById(R.id.passwordEditText)
        forgot = findViewById(R.id.reset_button)

        forgot.setOnClickListener {
            navigateToLogin()
        }
    }
}