package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class EditItemPage: AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var cancel2: Button
    private lateinit var save2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.edititempage)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        cancel2 = findViewById(R.id.cancelbuttonEditPage)
        save2 = findViewById(R.id.savebuttonEditPage)

        cancel2.setOnClickListener {
            navigateToYourItemPage1()
        }

        save2.setOnClickListener {
            navigateToYourItemPage2()
        }
    }


    private fun navigateToYourItemPage1(){
        val intent = Intent(this, YourItemPage::class.java)
        startActivity(intent)
    }
    private fun navigateToYourItemPage2(){
        val intent = Intent(this, YourItemPage::class.java)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation item clicks
        when (item.itemId) {
            R.id.home -> {
                val intent = Intent(this, ArticlesList::class.java)
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