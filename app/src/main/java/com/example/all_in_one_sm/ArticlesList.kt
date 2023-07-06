package com.example.all_in_one_sm
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request


class ArticlesList : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    data class Items(
        val id: String,
        val titulo: String,
        val marca: String,
        val cor: String,
        val tamanho: String,
        val categoria: String,
        val estado: String
    )

    private lateinit var itemList: ArrayList<Items>
    private lateinit var preferences: SharedPreferences
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.articleslist)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        preferences = getSharedPreferences("Article", Context.MODE_PRIVATE)

        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
        val savedUsername = prefs.getString("username", null)

        val displayNome = findViewById<TextView>(R.id.nameUser)
        displayNome.text = "Hello $savedUsername"


    }

    override fun onResume() {
        super.onResume()
        retrieveAddressesFromApi()
    }

    private fun retrieveAddressesFromApi() {
        Thread {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://my-json-server.typicode.com/a41792/FakeApi/items")
                .build()

            client.newCall(request).execute().use { response ->
                val gson = Gson()
                val article = object : TypeToken<List<Items>>() {}.type

                val apiAddresses = gson.fromJson<List<Items>>(response.body?.string(), article)


                runOnUiThread {
                    retrieveArticle(apiAddresses)
                    updateArticle()
                }
            }
        }.start()
    }

    private fun retrieveArticle(apiAddresses: List<Items>) {
        val json = preferences.getString("article", "")
        if (json != null && json.isNotEmpty()) {
            val gson = Gson()
            val addressesType = object : TypeToken<ArrayList<Items>>() {}.type
            itemList = gson.fromJson(json, addressesType)

            itemList.addAll(apiAddresses)
        } else {
            itemList = ArrayList(apiAddresses)
        }
    }

    private fun updateArticle() {
        val articleListView: ListView = findViewById(R.id.addressListView)
        articleListView.adapter = AddressAdapter()
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
            val view = layoutInflater.inflate(R.layout.articleitem, null)

            val titulo: TextView = view.findViewById(R.id.titulo)
            val marca: TextView = view.findViewById(R.id.marca)
            val cor: TextView = view.findViewById(R.id.cor)

            val article = itemList[position]

            titulo.text = "${article.titulo}"
            marca.text = "${article.marca}"
            cor.text = "${article.cor}"

            return view
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