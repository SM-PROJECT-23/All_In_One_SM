package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.all_in_one_sm.YourArticle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*


data class ItemModel(
    @SerializedName("id")
    var id: UUID,
    @SerializedName("titulo")
    val titulo: String? = "",
    @SerializedName("marca")
    val marca: String? = "",
    @SerializedName("categoria")
    val categoria: String? = "",
    @SerializedName("tamanho")
    val tamanho: String? = "",
    @SerializedName("cor")
    val cor: String? = "",
    @SerializedName("estado")
    val estado: String? = ""
)

class AddItemPage: AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    private lateinit var cancel1: Button
    private lateinit var save1: Button
    private lateinit var go1: Button

    private lateinit var titulo: EditText
    private lateinit var marca: EditText
    private lateinit var categoria: EditText
    private lateinit var tamanho: EditText
    private lateinit var cor: EditText
    private lateinit var estado: EditText

    @OptIn(DelicateCoroutinesApi::class)
    private fun registerItem(item: ItemModel) {

        val url = "https://my-json-server.typicode.com/a41792/FakeApi"

        val requestBody = """
        {
            "id": "${item.id}",
            "titulo": "${item.titulo}",
            "marca": "${item.marca}",
            "categoria": "${item.categoria}",
            "tamanho": "${item.tamanho}",
            "cor": "${item.cor}",
            "estado": "${item.estado}" 
        }
    """.trimIndent()

        val mediaType = "application/json; charset=utf-8".toMediaType()

        val client = okhttp3.OkHttpClient()

        // Create a request with the JSON body
        val request = Request.Builder()
            .url(url)
            .post(requestBody.toRequestBody(mediaType))
            .build()

        // Make the API call in a coroutine to avoid blocking the main thread
        try {
            // Send the request and retrieve the response
            val response = client.newCall(request).execute()

            val registerResponse = response.body?.string()

            if (response.isSuccessful) {
                println("Add Item Successful!")
            }else {
                println("Add Item Failed. Response Code: $registerResponse")
            }

        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.additem)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        cancel1 = findViewById(R.id.cancelbuttonAddPage1)
        save1 = findViewById(R.id.savebuttonAddPage1)
        go1 = findViewById(R.id.gobuttonAddPage1)

        titulo = findViewById(R.id.tituloEditText)
        marca = findViewById(R.id.marcaEditText)
        categoria = findViewById(R.id.categoriaEditText)
        tamanho = findViewById(R.id.tamanhoEditText)
        cor = findViewById(R.id.corEditText)
        estado = findViewById(R.id.estadoEditText)

        go1.setOnClickListener {
            navigateToYourItemPage()
        }

        cancel1.setOnClickListener {
            navigateToYourArticlePage()
        }

        save1.setOnClickListener {
            // Perform registration logic here
            CoroutineScope(Dispatchers.IO).launch{
                val newItem = ItemModel(
                    id = UUID.randomUUID(),
                    titulo.text.toString(),
                    marca.text.toString(),
                    categoria.text.toString(),
                    tamanho.text.toString(),
                    cor.text.toString(),
                    estado.text.toString()
                )
                registerItem(newItem)
                navigateToYourItemPage()
            }
           // navigateToYourItemPage()
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