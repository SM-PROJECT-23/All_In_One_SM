package com.example.all_in_one_sm
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

class LoginPage : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var forgotPassword: TextView
    private lateinit var register: TextView
    private lateinit var login: Button

     private fun performLogin(username: String, password: String) {

         val client = OkHttpClient()

         val url = "http://192.168.1.104:3000/login"

         val formBody = FormBody.Builder()
             .add("username", username)
             .add("password", password)
             .build()

         val request = Request.Builder()
             .url(url)
             .post(formBody)
             .build()

         client.newCall(request).enqueue(object : Callback {
             override fun onFailure(call: Call, e: IOException) {
                 // Handle network error
                 e.printStackTrace()
             }

             override fun onResponse(call: Call, response: Response) {
                 response.use { response ->
                     if (!response.isSuccessful) {
                         // Handle non-successful response
                         println("Error: ${response.code}")
                         return
                     }

                     val responseData = response.body?.string()
                     // Process the response data
                     println("Response: Login Sucessfull! $responseData")
                 }
             }
         })
     }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterPage::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Initialize views
        username = findViewById(R.id.usernameEditText)
        password = findViewById(R.id.passwordEditText)
        forgotPassword = findViewById(R.id.forgot_password_text)
        register = findViewById(R.id.register_text)
        login = findViewById(R.id.login_button)

        // Set click listeners
        forgotPassword.setOnClickListener {
            // Handle "Forgot password" click
            // Implement your logic here
        }

        login.setOnClickListener {
            // Perform login action
            performLogin(username.text.toString(), password.text.toString())
            navigateToHome()
            /*if (isLoggedIn) {
                navigateToHome()
            }*/
        }

        register.setOnClickListener {
            navigateToRegister()
        }
    }
}