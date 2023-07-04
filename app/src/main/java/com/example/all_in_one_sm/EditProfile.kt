package com.example.all_in_one_sm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText

class EditProfile : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var countryEditText: TextInputEditText
    private lateinit var cityEditText: TextInputEditText
    private lateinit var oldPasswordEditText: TextInputEditText
    private lateinit var newPasswordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var currentUser: UserEP

    private fun navigateToProfile(updatedUser: UserEP) {
        val intent = Intent(this, ProfilePage::class.java)
        intent.putExtra("updatedUser", updatedUser)
        startActivity(intent)
        finish()
    }

    private fun updateUser(name: String, email: String, city: String, country: String, pass: String) {
        println("Updated name: $name")
        println("Updated email: $email")
        println("Updated country: $country")
        println("Updated city: $city")
        println("Updated password: $pass")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.editprofile)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
        val savedUsername = prefs.getString("username", null)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditEditText)
        countryEditText = findViewById(R.id.countryEditText)
        cityEditText = findViewById(R.id.cityEditText)
        oldPasswordEditText = findViewById(R.id.oldpasswordEditText)
        newPasswordEditText = findViewById(R.id.newpasswordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)

        val cancelButton: Button = findViewById(R.id.CancelButton)
        val saveButton: Button = findViewById(R.id.SaveButton)

        getUserData(savedUsername)?.let { userData ->
            currentUser = userData
            displayUserInfo(currentUser)
        } ?: run {
            println("erro!")
        }

        cancelButton.setOnClickListener {
            navigateToProfile(currentUser)
        }

        saveButton.setOnClickListener {
            val updatedName = nameEditText.text.toString()
            val updatedEmail = emailEditText.text.toString()
            val updatedCountry = countryEditText.text.toString()
            val updatedCity = cityEditText.text.toString()
            val updatedPass = confirmPasswordEditText.text.toString()

            // Perform validation if needed
            if (updatedName.isNotEmpty() && updatedEmail.isNotEmpty()) {
                // Update the user information
                updateUser(updatedName, updatedEmail, updatedCountry, updatedCity, updatedPass)
                println("Update successful!")

                // Create the updated user object
                val updatedUser = UserEP(updatedName, savedUsername, updatedPass, updatedEmail, updatedCountry, updatedCity)

                // Navigate to the Profile page and pass the updated user object
                navigateToProfile(updatedUser)

                // Finish the activity
                finish()
            } else {
                println("Validation failed!")
            }
        }
    }

    private fun displayUserInfo(user: UserEP) {
        nameEditText.setText(user.name)
        emailEditText.setText(user.email)
        countryEditText.setText(user.country)
        cityEditText.setText(user.city)
        oldPasswordEditText.setText(user.password)
        newPasswordEditText.setText(user.password)
        confirmPasswordEditText.setText(user.password)
    }

    private fun getUserData(username: String?): UserEP? {
        val userList = listOf(
            UserEP("hello", "ola", "abc", "ola@teste.com", "br", "pt"),
            UserEP("test", "teste", "abc", "teste@test.com", "br", "pt"),
            UserEP("xripiti", "xpto", "abc", "xpto@test.com", "br", "pt")
        )


        return userList.find { it.username == username }
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