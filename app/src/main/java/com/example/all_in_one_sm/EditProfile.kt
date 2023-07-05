package com.example.all_in_one_sm
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.editprofile.*

class EditProfile : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var countryEditText: TextInputEditText
    private lateinit var cityEditText: TextInputEditText
    private lateinit var phoneEditText: TextInputEditText
    private lateinit var oldPasswordEditText: TextInputEditText
    private lateinit var newPasswordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var currentUser: User

    private fun navigateToProfile(updatedUser: User) {
        val intent = Intent(this, ProfilePage::class.java)
        intent.putExtra("updatedUser", updatedUser)
        startActivity(intent)
        finish()
    }

    private fun updateUser(name: String, email: String, phone: String, city: String, country: String, pass: String) {
        currentUser.name = name
        currentUser.email = email
        currentUser.phone = phone
        currentUser.city = city
        currentUser.country = country
        currentUser.password = pass
    }

    private fun saveUserToPrefs(user: User) {
        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("name", user.name)
        editor.putString("username", user.username)
        editor.putString("password", user.password)
        editor.putString("email", user.email)
        editor.putString("phone", user.phone)
        editor.putString("country", user.country)
        editor.putString("city", user.city)
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title =
            Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.editprofile)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
        val savedUsername = prefs.getString("username", null)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditEditText)
        countryEditText = findViewById(R.id.countryEditText)
        cityEditText = findViewById(R.id.cityEditText)
        phoneEditText = findViewById(R.id.PNEditText)
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
            val updatedPN = phoneEditText.text.toString()
            val updatedCity = cityEditText.text.toString()
            val updatedPass = if (newPasswordEditText.text.toString().isNotEmpty()) {
                confirmPasswordEditText.text.toString()
            } else {
                currentUser.password
            }

            // Perform validation if needed
            if (updatedName.isNotEmpty() && updatedEmail.isNotEmpty()) {
                // Update the user information
                if (updatedPass != null) {
                    updateUser(updatedName, updatedEmail, updatedCountry, updatedCity, updatedPN, updatedPass)
                }
                Toast.makeText(this, "Update successful!", Toast.LENGTH_LONG).show()
                // Create the updated user object
                val updatedUser = User(
                    updatedName,
                    savedUsername,
                    updatedPass,
                    updatedEmail,
                    updatedPN,
                    updatedCountry,
                    updatedCity
                )

                // Navigate to the Profile page and pass the updated user object
                saveUserToPrefs(updatedUser)
                navigateToProfile(updatedUser)

                // Finish the activity
                finish()
            } else {
                Toast.makeText(this, "Validation failed!", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun displayUserInfo(user: User) {
        nameEditText.setText(user.name)
        emailEditText.setText(user.email)
        countryEditText.setText(user.country)
        cityEditText.setText(user.city)
        phoneEditText.setText(user.phone)
        oldPasswordEditText.setText("")
        newPasswordEditText.setText("")
        confirmPasswordEditText.setText("")
    }

    private fun getUserData(username: String?): User? {
        val userList = listOf(
            User("hello", "ola", "abc", "ola@teste.com","678543290", "br", "pt"),
            User("test", "teste", "abc", "teste@test.com","987654321", "br", "pt"),
            User("xripiti", "xpto", "abc","xpto@test.com", "123456789", "br", "pt")
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