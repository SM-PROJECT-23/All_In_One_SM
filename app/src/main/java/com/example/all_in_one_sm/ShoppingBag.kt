package com.example.all_in_one_sm
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.all_in_one_sm.YourArticle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request


class ShoppingBag : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener  {

    data class ItemsBag(
        val id: String,
        val titulo: String,
        val marca: String,
        val cor: String,
        val tamanho: String,
        val categoria: String,
        val estado: String,
        val preco: String
    )

    private lateinit var itemList: ArrayList<ItemsBag>
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.shoppingbag)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val payButton: Button = findViewById(R.id.pay_button)
        val failButton: Button = findViewById(R.id.cencel_button)

        preferences = getSharedPreferences("Article", Context.MODE_PRIVATE)


        payButton.setOnClickListener{
            navigateToPay()
        }

        failButton.setOnClickListener{
            navigateToCancel()
        }
    }

    private fun navigateToPay() {
        val intent = Intent(this, SuccessfullPayment::class.java)
        startActivity(intent)
    }

    private fun navigateToCancel() {
        val intent = Intent(this, FailPayment::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        retrieveItemsFromApi()
    }

    private fun retrieveItemsFromApi() {
        Thread {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://my-json-server.typicode.com/a41792/FakeApi/bag")
                .build()

            client.newCall(request).execute().use { response ->
                val gson = Gson()
                val article = object : TypeToken<List<ItemsBag>>() {}.type

                val apiAddresses = gson.fromJson<List<ItemsBag>>(response.body?.string(), article)


                runOnUiThread {
                    retrieveArticle(apiAddresses)
                    updateArticle()
                }
            }
        }.start()
    }
    private fun retrieveArticle(apiItems: List<ItemsBag>) {
        val json = preferences.getString("article", "")
        if (json != null && json.isNotEmpty()) {
            val gson = Gson()
            val addressesType = object : TypeToken<ArrayList<ItemsBag>>() {}.type
            itemList = gson.fromJson(json, addressesType)

            itemList.addAll(apiItems)
        } else {
            itemList = ArrayList(apiItems)
        }
    }

    private fun updateArticle() {
        val articleListView: ListView = findViewById(R.id.bagListView)
        articleListView.adapter = AddressAdapter()
    }
    private fun saveArticle() {
        val edit = preferences.edit()
        val gson = Gson()
        val articleJson = gson.toJson(itemList)
        edit.putString("Article", articleJson)
        edit.apply()
    }

    private fun removeArticle(position: Int) {
        itemList.removeAt(position)
        saveArticle()
        updateArticle()
    }

    private fun calculateTotalPrice(): Double {
        var totalPrice = 0.0
        for (item in itemList) {
            val price = item.preco.toDoubleOrNull() ?: continue
            totalPrice += price
        }
        return totalPrice
    }

    inner class AddressAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return itemList.size
        }

        override fun getItem(position: Int): Any {
            return itemList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.bagitem, null)

            val titulo: TextView = view.findViewById(R.id.titulobag)
            val marca: TextView = view.findViewById(R.id.marcabag)
            val preco: TextView = view.findViewById(R.id.precobag)
            val remove: TextView = view.findViewById(R.id.removebag)
            val total: TextView = findViewById(R.id.total)

            val article = itemList[position]

            titulo.text = "${article.titulo}"
            marca.text = "Brand: ${article.marca}"
            preco.text = "Price: ${article.preco}€"
            total.text = "Total: ${calculateTotalPrice()}€"

            remove.setOnClickListener {
                removeArticle(position)
            }
            return view
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
