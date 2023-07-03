package com.example.all_in_one_sm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class Email : AppCompatActivity() {

    private lateinit var OldPass : TextInputEditText
    private lateinit var goReset : Button

    private fun navigateToResetPass() {
        val intent = Intent(this, ForgotPassword::class.java)
        startActivity(intent)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = Html.fromHtml("<font color=\"white\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.email)

        // Initialize views
        OldPass = findViewById(R.id.usernameEditText)
        goReset = findViewById(R.id.login_button1)

        goReset.setOnClickListener {
            navigateToResetPass()
        }
    }
}