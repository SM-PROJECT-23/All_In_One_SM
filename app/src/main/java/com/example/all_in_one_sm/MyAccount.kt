package com.example.all_in_one_sm
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.all_in_one_sm.YourArticle
import com.google.android.material.bottomnavigation.BottomNavigationView


class MyAccount : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.myaccount)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

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
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation item clicks
        when (item.itemId) {
            R.id.home -> {
                val intent = Intent(this, YourArticle::class.java)
                startActivity(intent)
                return true
            }
            R.id.shop -> {
                val intent = Intent(this, ShoppingBag::class.java)
                startActivity(intent)
                return true
            }
            R.id.add -> {
                val intent = Intent(this, AddItemPage::class.java)
                startActivity(intent)
                return true
            }
            R.id.fav -> {
                val intent = Intent(this, FavoritesPage::class.java)
                startActivity(intent)
                return true
            }
            R.id.user -> {
                val intent = Intent(this, MyAccount::class.java)
                startActivity(intent)
                return true
            }
            // Add more cases for each menu item
        }
        return false
    }

}
