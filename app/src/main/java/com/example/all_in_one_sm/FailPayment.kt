package com.example.all_in_one_sm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FailPayment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.failpayment)

        val returnButton: Button = findViewById(R.id.return_button)
        returnButton.setOnClickListener {
            // Navigate to the ShoppingBag activity
            val intent = Intent(this, ShoppingBag::class.java)
            startActivity(intent)
        }
    }
}
