package com.example.all_in_one_sm
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MyAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myaccount)

        ///val ordersButton: Button = findViewById(R.id.ordersButton)
        val profileButton: Button = findViewById(R.id.profileButton)
        val itemsButton: Button = findViewById(R.id.itemsButton)
        val addressButton: Button = findViewById(R.id.addressButton)
        val logoutButton: Button = findViewById(R.id.logout_button)

        val ordersButton: Button = findViewById(R.id.ordersButton)
        val color = ContextCompat.getColor(this, R.color.white)
        val colorStateList = ColorStateList.valueOf(color)
        ordersButton.backgroundTintList = colorStateList
        itemsButton.backgroundTintList = colorStateList
        addressButton.backgroundTintList = colorStateList
        profileButton.backgroundTintList = colorStateList


        ordersButton.setOnClickListener {
            // Perform actions for the "Orders" button click
        }

        profileButton.setOnClickListener {
            // Perform actions for the "Profile" button click
        }

        itemsButton.setOnClickListener {
            // Perform actions for the "Your Items" button click
        }

        addressButton.setOnClickListener {
            // Perform actions for the "Address Book" button click
        }

        logoutButton.setOnClickListener {
            // Perform actions for the "Logout" button click
        }
    }
}
