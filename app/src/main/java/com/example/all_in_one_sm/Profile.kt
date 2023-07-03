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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

data class User(
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("phoneNumber")
    var phoneNumber: String?,
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

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title =
            Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.profile)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val username = prefs.getString("username", "")

        // Initialize the views
        nameTextView = findViewById(R.id.nameTextView)
        usernameTextView = findViewById(R.id.usernameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        countryTextView = findViewById(R.id.countryTextView)
        cityTextView = findViewById(R.id.cityTextView)
        passwordTextView = findViewById(R.id.passwordTextView)

        // Set up the BottomNavigationView and edit button
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val editButton: Button = findViewById(R.id.EditProfileButton)
        editButton.setOnClickListener {
            navigateToEditProfile()
        }

        if (username != null) {
            fetchUser(username) { user ->
                runOnUiThread {
                    // Set the text of TextView elements with the item's properties
                    usernameTextView.text = user.username
                    nameTextView.text = user.name
                    emailTextView.text = user.email
                    phoneTextView.text = user.phoneNumber
                    cityTextView.text = user.city
                    countryTextView.text = user.country
                    passwordTextView.text = user.password
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun fetchUser(username: String, callback:(User) -> Unit) {

        val baseUrl = "http://192.168.1.104:3000/people/$username"

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(baseUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val json = responseBody.string()

                    val user = Gson().fromJson(json, User::class.java)

                    callback(user)

                    runOnUiThread {
                        // Update the UI with the retrieved user details
                        usernameTextView.text = user.username
                        nameTextView.text = user.name
                        emailTextView.text = user.email
                        phoneTextView.text = user.phoneNumber
                        countryTextView.text = user.country
                        cityTextView.text = user.city
                        passwordTextView.text = user.password
                    }
                }
            }
        })
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