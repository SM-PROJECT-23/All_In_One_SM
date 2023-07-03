package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.*

class YourItemPage: AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var remove1: Button
    private lateinit var edit1: Button


    private lateinit var tituloTextView: TextView
    private lateinit var marcaTextView: TextView
    private lateinit var categoriaTextView: TextView
    private lateinit var tamanhoTextView: TextView
    private lateinit var corTextView: TextView
    private lateinit var estadoTextView: TextView



    private fun navigateToAddItemPage() {
        val intent = Intent(this, AddItemPage::class.java)
        startActivity(intent)
    }

    private fun navigateToEditItemPage() {
        val intent = Intent(this, EditItemPage::class.java)
        startActivity(intent)
    }

    private fun fetchItemDetails(itemId: String, callback: (ItemModel) -> Unit) {
        val url = "http://192.168.1.72:3000/items/$itemId" // Replace with your API endpoint
        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // Handle the failure case
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val json = responseBody.string()

                    // Parse the JSON response into an ItemModel object
                    val item = Gson().fromJson(json, ItemModel::class.java)

                    // Invoke the callback with the retrieved item details
                    callback(item)

                    // Update the UI with the retrieved item details
                    runOnUiThread {
                        // Set the text of TextView elements with the item's properties
                        tituloTextView.text = item.titulo
                        marcaTextView.text = item.marca
                        categoriaTextView.text = item.categoria
                        tamanhoTextView.text = item.tamanho
                        corTextView.text = item.cor
                        estadoTextView.text = item.estado
                    }
                }
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.youritempage)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        tituloTextView = findViewById(R.id.tituloTextView)
        marcaTextView = findViewById(R.id.marcaTextView)
        categoriaTextView = findViewById(R.id.categoriaTextView)
        tamanhoTextView = findViewById(R.id.tamanhoTextView)
        corTextView = findViewById(R.id.corTextView)
        estadoTextView = findViewById(R.id.estadoTextView)

        fetchItemDetails("154db25f-0c73-4461-9c41-6f379628773a") { item ->
            runOnUiThread {
                // Set the text of TextView elements with the item's properties
                tituloTextView.text = item.titulo
                marcaTextView.text = item.marca
                categoriaTextView.text = item.categoria
                tamanhoTextView.text = item.tamanho
                corTextView.text = item.cor
                estadoTextView.text = item.estado
            }
        }

        remove1 = findViewById(R.id.removebuttonYourPage)
        edit1 = findViewById(R.id.editbuttonYourPage)

        remove1.setOnClickListener {
            navigateToAddItemPage()
        }

        edit1.setOnClickListener {
            navigateToEditItemPage()
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