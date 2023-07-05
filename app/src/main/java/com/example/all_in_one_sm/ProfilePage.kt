package com.example.all_in_one_sm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

data class User(
    @SerializedName("name")
    var name: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("country")
    var country: String?,
    @SerializedName("city")
    var city: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(email)
        parcel.writeString(country)
        parcel.writeString(city)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

class ProfilePage : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var nameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var countryTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var passwordTextView: TextView

    private var isUserDataUpdated = false
    private lateinit var user: User
    private lateinit var currentUser: User

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditProfile::class.java)
        intent.putExtra("currentUser", user)
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

    private fun loadUserData(): User? {
        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("userData", null)
        val type = object : TypeToken<User>() {}.type
        return gson.fromJson(json, type)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n", "MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title =
            Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.profile)

        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)

        // Initialize the views
        nameTextView = findViewById(R.id.UserNameTextView)
        usernameTextView = findViewById(R.id.usernameValueTextView)
        emailTextView = findViewById(R.id.emailValueTextView)
        countryTextView = findViewById(R.id.CountryTextView)
        cityTextView = findViewById(R.id.CityTextView)
        passwordTextView = findViewById(R.id.passTextView)

        val savedUsername = prefs.getString("username", null)
        val savedName = prefs.getString("name", null)
        val savedEmail = prefs.getString("email", null)
        val savedPassword = prefs.getString("password", null)
        val savedCountry = prefs.getString("country", null)
        val savedCity = prefs.getString("city", null)

        if (savedUsername != null && savedName != null && savedEmail != null && savedPassword != null && savedCountry != null && savedCity != null) {
            user = User(savedName, savedUsername, savedPassword, savedEmail, savedCountry, savedCity)
            displayUserInfo(user)
        }

        if (!isUserDataUpdated && savedUsername != null) {
            GlobalScope.launch(Dispatchers.Main) {
                user = fetchUser(savedUsername)!!
                displayUserInfo(user)
            }
        }

        // Set up the BottomNavigationView and edit button
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val editButton: Button = findViewById(R.id.EditProfileButton)
        editButton.setOnClickListener {
            navigateToEditProfile()
        }

        if (savedUsername != null) {
            GlobalScope.launch(Dispatchers.Main) {
                user = fetchUser(savedUsername)!!
                displayUserInfo(user)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // Fetch user data from the intent
        val updatedUser: User? = intent.getParcelableExtra("updatedUser")

        if (updatedUser != null) {
            currentUser = updatedUser
            displayUserInfo(currentUser)
            isUserDataUpdated = true
        } else if (!isUserDataUpdated) {
            // Fetch user data from the saved username
            val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
            val savedUsername = prefs.getString("username", null)

            if (savedUsername != null) {
                CoroutineScope(Dispatchers.Main).launch {
                    val userUpdate = withContext(Dispatchers.IO) {
                        fetchUser(savedUsername)
                    }
                    if (userUpdate != null) {
                        currentUser = userUpdate
                        displayUserInfo(currentUser)
                    } else {
                        println("User data not found!")
                    }
                }
            }
        }
    }

    private suspend fun fetchUser(username: String): User? {
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
                    // Assuming the JSON response is an array of user objects
                    val users = parseJsonArray(json)
                    if (users.isNotEmpty()) {
                        val userEP = users[0]
                        return@withContext User(
                            name = userEP.name,
                            username = userEP.username,
                            password = userEP.password,
                            email = userEP.email,
                            country = userEP.country,
                            city = userEP.city
                        )
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

    private fun parseJsonArray(json: String): List<User> {
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