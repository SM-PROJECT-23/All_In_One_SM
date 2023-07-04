package com.example.all_in_one_sm
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
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

        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
        val savedUsername = prefs.getString("username", null)

        val displayNome = findViewById<TextView>(R.id.nameUser)
        displayNome.text = "$savedUsername"

        fun logout() {
            // Clear any saved user session or preferences
            val prefs = getSharedPreferences("com.example.all_in_one_sm", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()

            // Navigate the user back to the login page
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }


        ordersButton.setOnClickListener {
            val intent = Intent(this, Orders::class.java)
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        itemsButton.setOnClickListener {
            val intent = Intent(this, YourItemPage::class.java)
            startActivity(intent)
        }

        addressButton.setOnClickListener {
            val intent = Intent(this, AddressBook::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            logout()
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
