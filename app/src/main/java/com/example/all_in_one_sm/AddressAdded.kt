package com.example.all_in_one_sm

import android.annotation.SuppressLint
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
import okhttp3.OkHttpClient
import okhttp3.Request

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
        retrieveAddressesFromApi()
    }

    private fun retrieveAddressesFromApi() {
        Thread {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://my-json-server.typicode.com/a41792/FakeApi/addresses")
                .build()

            client.newCall(request).execute().use { response ->
                val gson = Gson()
                val addressesType = object : TypeToken<List<Address>>() {}.type
                // Parse the response body to a List of Address objects
                val apiAddresses = gson.fromJson<List<Address>>(response.body?.string(), addressesType)

                // Update the list on the main thread
                runOnUiThread {
                    retrieveAddressesFromSharedPreferences(apiAddresses)
                    updateAddressListView()
                }
            }
        }.start()
    }

    private fun retrieveAddressesFromSharedPreferences(apiAddresses: List<Address>) {
        val addressJson = preferences.getString("addressList", "")
        if (addressJson != null && addressJson.isNotEmpty()) {
            val gson = Gson()
            val addressesType = object : TypeToken<ArrayList<Address>>() {}.type
            addressList = gson.fromJson(addressJson, addressesType)

            // Merge API addresses with locally stored ones
            addressList.addAll(apiAddresses)
        } else {
            addressList = ArrayList(apiAddresses)
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

        @SuppressLint("SetTextI18n", "ViewHolder", "InflateParams")
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
