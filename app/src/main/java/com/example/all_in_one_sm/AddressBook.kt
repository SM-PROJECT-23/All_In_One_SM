package com.example.all_in_one_sm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

class AddressBook : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.activity_address_book)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val countries = arrayOf("USA", "Canada", "UK", "PT", "SPN", "IT") // Add more countries as needed
        val spinner: Spinner = findViewById(R.id.countrySpinner)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries)
        spinner.adapter = adapter

        preferences = getSharedPreferences("AddressBook", Context.MODE_PRIVATE)

        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            // Retrieve data from EditTexts and Spinner
            val name = findViewById<EditText>(R.id.editTextName).text.toString()
            val country = spinner.selectedItem.toString()
            val state = findViewById<EditText>(R.id.editTextState).text.toString()
            val city = findViewById<EditText>(R.id.editTextCity).text.toString()
            val postalCode = findViewById<EditText>(R.id.editTextPostalCode).text.toString()
            val address = findViewById<EditText>(R.id.editTextAddress).text.toString()
            val phoneNumber = findViewById<EditText>(R.id.editTextPhoneNumber).text.toString()

            // Save address in SharedPreferences
            val editor = preferences.edit()
            editor.putString("name", name)
            editor.putString("country", country)
            editor.putString("state", state)
            editor.putString("city", city)
            editor.putString("postalCode", postalCode)
            editor.putString("address", address)
            editor.putString("phoneNumber", phoneNumber)
            editor.apply()

            val newAddress = Address(
                id = UUID.randomUUID().toString(),
                name = name!!,
                country = country!!,
                state = state!!,
                city = city!!,
                postalCode = postalCode!!,
                address = address!!,
                phoneNumber = phoneNumber!!
            )

            val gson = Gson()
            val addressListJson = preferences.getString("addressList", "[]")
            val addressListType = object : TypeToken<List<Address>>(){}.type
            var addressList = gson.fromJson<List<Address>>(addressListJson, addressListType).toMutableList()
            addressList.add(newAddress)
            preferences.edit().putString("addressList", gson.toJson(addressList)).apply()

            // Navigate to AddressAdded activity
            val intent = Intent(this, AddressAdded::class.java)
            startActivity(intent)
        }
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
