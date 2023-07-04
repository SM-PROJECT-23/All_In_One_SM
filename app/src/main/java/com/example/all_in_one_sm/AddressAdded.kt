package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddressAdded : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var addressList: ArrayList<Address>  // Replace with your Address class
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.address_added)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        preferences = getSharedPreferences("AddressBook", Context.MODE_PRIVATE)

        val addAddressButton: Button = findViewById(R.id.addAddressButton)
        addAddressButton.setOnClickListener {
            // Start your Add Address activity here
            val intent = Intent(this, AddressBook::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        retrieveAddressesFromSharedPreferences()
        updateAddressListView()
    }

    private fun retrieveAddressesFromSharedPreferences() {
        val addressJson = preferences.getString("addressList", "")
        if (addressJson != null && addressJson.isNotEmpty()) {
            val gson = Gson()
            val addressesType = object : TypeToken<ArrayList<Address>>() {}.type
            addressList = gson.fromJson(addressJson, addressesType)
        } else {
            addressList = ArrayList()
        }
    }

    private fun updateAddressListView() {
        val addressListView: ListView = findViewById(R.id.addressListView)
        addressListView.adapter = AddressAdapter()
    }

    inner class AddressAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return addressList.size
        }

        override fun getItem(position: Int): Any {
            return addressList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.address_item, null)

            val addressInfo: TextView = view.findViewById(R.id.addressInfo)
            val removeLink: TextView = view.findViewById(R.id.removeLink)

            val address = addressList[position]

            // Set your address info here
            addressInfo.text = "${address.name}, ${address.address}, ${address.city}, ${address.state}, ${address.postalCode}"

            removeLink.setOnClickListener {
                // Handle your Remove action here
                removeAddress(position)
            }

            return view
        }
    }

    private fun removeAddress(position: Int) {
        addressList.removeAt(position)
        saveAddressesToSharedPreferences()
        updateAddressListView()
    }

    private fun saveAddressesToSharedPreferences() {
        val editor = preferences.edit()
        val gson = Gson()
        val addressJson = gson.toJson(addressList)
        editor.putString("addressList", addressJson)
        editor.apply()
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
