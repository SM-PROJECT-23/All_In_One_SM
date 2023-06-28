package com.example.all_in_one_sm
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ShoppingBag : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shoppingbag)

        val payButton: Button = findViewById(R.id.pay_button)

        payButton.setOnClickListener {
            // Perform the payment functionality
        }
    }
}
