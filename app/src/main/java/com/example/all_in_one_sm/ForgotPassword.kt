package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class ForgotPassword : AppCompatActivity() {

    private lateinit var OldPass : TextInputEditText
    private lateinit var NewPass : TextInputEditText
    private lateinit var forgot : Button
    private lateinit var confPass: TextInputEditText

    private fun navigateToLogin() {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = Html.fromHtml("<font color=\"white\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.forgotpassword)

        // Initialize views
        OldPass = findViewById(R.id.usernameEditText)
        NewPass = findViewById(R.id.passwordNew)
        confPass = findViewById(R.id.passwordEditText)

        forgot.setOnClickListener {
            navigateToLogin()
        }
    }
}