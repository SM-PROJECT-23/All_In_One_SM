package com.example.all_in_one_sm
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.all_in_one_sm.*
import com.example.all_in_one_sm.Endpoint.Endpoint
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticlesItem : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var card: LinearLayout
    private lateinit var name: TextView
    private lateinit var price: TextView
    private lateinit var size: TextView
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.item)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val baseUrl = "https://my-json-server.typicode.com/a41792/SMapi/"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val endpoint = retrofit.create(Endpoint::class.java)
        val call: Call<List<Items>> = endpoint.getItems()
        call.enqueue(object : Callback<List<Items>> {
            override fun onResponse(call: Call<List<Items>>, response: Response<List<Items>>) {
                if (response.isSuccessful) {
                    response.body()?.let { itemsList ->
                        if (itemsList.isNotEmpty()) {
                            val item = itemsList[0] // Get the first item from the list
                            updateViews(item)
                        }
                    }
                } else {
                    showToast("Failed to get items")
                }
            }

            override fun onFailure(call: Call<List<Items>>, t: Throwable) {
                showToast("Failed to get items: ${t.message}")
            }
        })
    }

    private fun updateViews(item: Items) {
        card = findViewById(R.id.card3)
        image = findViewById(R.id.image1)
        name = findViewById(R.id.textName)
        price = findViewById(R.id.textPrice)
        size = findViewById(R.id.textSize)

        // Update the views with data from the item
        image.setImageResource(R.drawable.ic_home)
        name.text = item.titulo
        price.text = item.categoria
        size.text = item.tamanho
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    data class Items(
        @SerializedName("id")
        var id: Int,
        @SerializedName("titulo")
        var titulo: String,
        @SerializedName("marca")
        var marca: String,
        @SerializedName("categoria")
        var categoria: String,
        @SerializedName("tamanho")
        var tamanho: String,
        @SerializedName("cor")
        var cor: String,
        @SerializedName("estado")
        var estado: String
    )

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
