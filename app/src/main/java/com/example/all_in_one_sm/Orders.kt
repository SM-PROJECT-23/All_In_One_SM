package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class Orders : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var orders: List<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.activity_order)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        // Make the network request on a separate thread to avoid blocking the UI
        Thread {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://my-json-server.typicode.com/a41792/FakeApi/orders")
                .build()

            client.newCall(request).execute().use { response ->
                val gson = Gson()
                val ordersType = object : TypeToken<List<Order>>() {}.type
                // Parse the response body to a List of Order objects
                orders = gson.fromJson<List<Order>>(response.body?.string(), ordersType)

                // Update the RecyclerView on the main thread
                runOnUiThread {
                    val orderListRecyclerView: RecyclerView = findViewById(R.id.orderListRecyclerView)
                    orderListRecyclerView.layoutManager = LinearLayoutManager(this)

                    // Adding divider
                    val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
                    orderListRecyclerView.addItemDecoration(dividerItemDecoration)

                    orderListRecyclerView.adapter = OrderAdapter(orders)
                }
            }
        }.start()
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
