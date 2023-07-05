package com.example.all_in_one_sm
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.editprofile.*
import kotlinx.android.synthetic.main.register.*

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*

data class UserModel(
    var id: UUID,
    @SerializedName("id")
    val name: String ="",
    @SerializedName("name")
    val username:String?="",
    @SerializedName("username")
    val email:String?="",
    @SerializedName("email")
    var phoneNumber: String,
    @SerializedName("phoneNumber")
    val country:String?="",
    @SerializedName("country")
    val city:String?="",
    @SerializedName("city")
    val password:String?="",
    @SerializedName("password")
    val confirmPassword:String?="",
)

class RegisterPage : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var country: EditText
    private lateinit var city: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var loginAccount: TextView
    private lateinit var loginAccount1: TextView


    @OptIn(DelicateCoroutinesApi::class)
    private fun registerUser(user: UserModel) {

        val url = "https://my-json-server.typicode.com/a41792/FakeApi/people"

        val requestBody = """
        {
            "id": "${user.id}",
            "name": "${user.name}",
            "username": "${user.username}",
            "email": "${user.email}",
            "phone number": "${user.phoneNumber}",
            "city": "${user.city}",
            "country": "${user.country}",
            "password": "${user.password}",
            "confirm pass": "${user.confirmPassword}"   
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
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(this, "Registration Failed. Response Code: $registerResponse", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                e.printStackTrace();
            }
    }

    private fun navigateToHome() {
        val intent = Intent(this, YourArticle::class.java)
        startActivity(intent)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.register)

        //id = findViewById(R.id.nameEditTextName)
        name = findViewById(R.id.nameEditText)
        username = findViewById(R.id.usernameEditText)
        email = findViewById(R.id.emailEditText)
        phone = findViewById(R.id.EditTextPhone)
        country = findViewById(R.id.countryEditText)
        city = findViewById(R.id.cityEditText)
        password = findViewById(R.id.passwordEditText)
        confirmPassword = findViewById(R.id.confirmPasswordEditText)
        registerButton = findViewById(R.id.registerButton)
        loginAccount = findViewById(R.id.loginAccountInput)
        loginAccount1 = findViewById(R.id.loginAccountInput1)

        loginAccount1.setTextColor(Color.parseColor("#1F63A6"));

        registerButton.setOnClickListener {
            // Perform registration logic here
            CoroutineScope(IO).launch{
                val newUser = UserModel(
                    id = UUID.randomUUID(),
                    name.text.toString(),
                    username.text.toString(),
                    email.text.toString(),
                    phone.text.toString(),
                    country.text.toString(),
                    city.text.toString(),
                    password.text.toString(),
                    confirmPassword.text.toString()
                )
                registerUser(newUser)
                navigateToLogin()
            }
            navigateToHome()
        }

        loginAccount1.setOnClickListener {
            navigateToLogin()
        }
    }
}

