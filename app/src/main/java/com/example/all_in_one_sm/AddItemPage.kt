package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.all_in_one_sm.YourArticle
import com.google.android.material.bottomnavigation.BottomNavigationView


class AddItemPage: AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var cancel1: Button
    private lateinit var save1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.additem)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        cancel1 = findViewById(R.id.cancelbuttonAddPage1)
        save1 = findViewById(R.id.savebuttonAddPage1)

        cancel1.setOnClickListener {
            navigateToYourArticlePage()
        }

        save1.setOnClickListener {
            navigateToYourItemPage()
        }

    }

    private fun navigateToYourArticlePage(){
        val intent = Intent(this, YourArticle::class.java)
        startActivity(intent)
    }

    private fun navigateToYourItemPage(){
        val intent = Intent(this, YourItemPage::class.java)
        startActivity(intent)
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