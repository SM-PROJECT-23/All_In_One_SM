package com.example.all_in_one_sm
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.*

data class User(
        @SerializedName("username")
        val username:String?="",
        @SerializedName("password")
        val password:String?="",
    )

class Profile : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var countryTextView: TextView
    private lateinit var passwordTextView: TextView
    private var user: Response? = null

    val baseUrl = "http://192.168.1.104:3000/people"

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditProfile::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        fetchUser()

        if (user != null){
            /*nameTextView.text.toString(),
            usernameTextView.text.toString(),
            emailTextView.text.toString(),
            phoneTextView.text.toString(),
            countryTextView.text.toString(),
            passwordTextView.text.toString()*/
            print(user)
        }

        nameTextView = findViewById(R.id.nameTextView)
        usernameTextView = findViewById(R.id.usernameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        countryTextView = findViewById(R.id.countryTextView)
        passwordTextView = findViewById(R.id.passwordTextView)

        val editButton: Button = findViewById(R.id.EditProfileButton)

        editButton.setOnClickListener {
            navigateToEditProfile()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SuspiciousIndentation")
    private fun fetchUser() {
        // Create an OkHttp client
        val client = OkHttpClient()

        // Create a request with the JSON body
        val request = Request.Builder()
            .url(baseUrl)
            .get()
            .build()

        // Make the API call in a coroutine to avoid blocking the main thread
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Send the request and retrieve the response
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val newResponse = response.body?.string()

                     val listUsers = Gson().fromJson<List<User?>>(
                        newResponse,
                        object : TypeToken<List<User?>?>() {}.type
                    )

                    // Process the list of users here
                    listUsers?.forEach { user ->
                        // Access user properties
                        println("User ID: ${user}")
                    }
                } else {
                    println("Error: ${response.code} ${response.message}")
                }
            } catch (e: Exception) {
                println("Exception occurred: ${e.message}")
                e.printStackTrace()
            }
        }
        }
}