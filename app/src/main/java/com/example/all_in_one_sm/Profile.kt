package com.example.all_in_one_sm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import okhttp3.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

data class User(
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("city")
    val city: String?
)

class Profile : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var nameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var countryTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var passwordTextView: TextView

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditProfile::class.java)
        startActivity(intent)
    }

    private fun displayUserInfo(user: User) {
        nameTextView.text = user.name
        usernameTextView.text = user.username
        emailTextView.text = user.email
        countryTextView.text = user.country
        cityTextView.text = user.city
        passwordTextView.text = user.password
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n", "MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title =
            Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.profile)

        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
        val savedUsername = prefs.getString("username", null)

        // Initialize the views
        nameTextView = findViewById(R.id.UserNameTextView)
        usernameTextView = findViewById(R.id.usernameValueTextView)
        emailTextView = findViewById(R.id.emailValueTextView)
        countryTextView = findViewById(R.id.CountryTextView)
        cityTextView = findViewById(R.id.CityTextView)
        passwordTextView = findViewById(R.id.passTextView)

        // Set up the BottomNavigationView and edit button
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val editButton: Button = findViewById(R.id.EditProfileButton)
        editButton.setOnClickListener {
            navigateToEditProfile()
        }

        if (savedUsername != null) {
            GlobalScope.launch(Dispatchers.Main) {
                val user = fetchUser(savedUsername)
                if (user != null) {
                    displayUserInfo(user)
                }
            }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    suspend fun fetchUser(username : String): User? {
        val apiUrl = "https://my-json-server.typicode.com/a41792/FakeApi/people?username=$username"

        val request = Request.Builder()
            .url(apiUrl)
            .build()

        val client = OkHttpClient()

        return withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val json = response.body?.string()

                if (response.isSuccessful && !json.isNullOrEmpty()) {
                    // Assuming the JSON response is an array of people objects
                    val people = parseJsonArray(json)
                    if (people.isNotEmpty()) {
                        return@withContext people[0]
                    }
                } else {
                    println("Failed to fetch user information. Response code: ${response.code}")
                }
            } catch (e: IOException) {
                // Handle network request failure
                e.printStackTrace()
                println("Failed to fetch user information: ${e.message}")
            }
            return@withContext null
        }
    }

    fun parseJsonArray(json: String): List<User> {
        val listType = object : TypeToken<List<User>>() {}.type
        return Gson().fromJson(json, listType)
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
        }
        return false
    }
}