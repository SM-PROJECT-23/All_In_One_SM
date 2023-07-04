package com.example.all_in_one_sm
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import okhttp3.Request

class EditProfile : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var phoneEditText: TextInputEditText
    private lateinit var countryEditText: TextInputEditText
    private lateinit var cityEditText: TextInputEditText
    private lateinit var oldPasswordEditText: TextInputEditText
    private lateinit var newPasswordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText

    private fun FetchUser(user: UserModel) {

        val url = "https://my-json-server.typicode.com/a41792/FakeApi/people"

        val requestBody = """
        {
            "id": "${user.id}",
            "name": "${user.name}",
            "username": "${user.username}",
            "email": "${user.email}",
            "city": "${user.city}",
            "country": "${user.country}",
            "password": "${user.password}"  
        }
    """.trimIndent()

        val client = okhttp3.OkHttpClient()

        // Create a request with the JSON body
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        // Make the API call in a coroutine to avoid blocking the main thread
        try {
            // Send the request and retrieve the response
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                println("Profile Open Successful!")
            }

        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    private fun navigateToProfile() {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity to prevent navigating back to the login page
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.editprofile)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditEditText)
        countryEditText = findViewById(R.id.countryEditText)
        cityEditText = findViewById(R.id.cityEditText)
        oldPasswordEditText = findViewById(R.id.oldpasswordEditText)
        newPasswordEditText = findViewById(R.id.newpasswordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)

        val cancelButton: Button = findViewById(R.id.CancelButton)
        val saveButton: Button = findViewById(R.id.SaveButton)

        // Add click listeners to buttons
        cancelButton.setOnClickListener {
            // Handle cancel button click
            navigateToProfile()
        }

        saveButton.setOnClickListener {
            // Handle save button click
            navigateToProfile()
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
