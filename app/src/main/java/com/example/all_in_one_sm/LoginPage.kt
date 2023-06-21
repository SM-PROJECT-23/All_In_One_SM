package com.example.all_in_one_sm
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.*

class LoginPage : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var forgotPassword: TextView
    private lateinit var register: TextView
    private lateinit var login: Button

    val baseUrl = "http://192.168.1.104:3000/login"

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterPage::class.java)
        startActivity(intent)
    }

    data class User(
        //val userId: Int?=0,
        @SerializedName("Username")
        val username:String?="",
        @SerializedName("Password")
        val password:String?="",
    )

    @SuppressLint("SuspiciousIndentation")
    @OptIn(DelicateCoroutinesApi::class)
    fun login(username: String, password: String, callback: (Boolean) -> Unit) {

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

                // Check if the login was successful (HTTP status code 200-299)
                //val loginSuccessful = response.isSuccessful

                if (response.isSuccessful) {
                    val loginResponse = response.body?.string()

                    val listUsers = Gson().fromJson<List<User?>>(
                        loginResponse,
                        object : TypeToken<List<User?>?>() {}.type
                    )

                    var isUser = false

                    for (user in listUsers) {
                        if ((user?.username == username) && (user.password == password)) {
                            isUser = true
                            break
                        }
                    }
                    if (isUser){
                        //Toast.makeText(applicationContext, "Login Successful!", Toast.LENGTH_SHORT).show()
                        println("Login com sucesso");

                            callback(true)

                    } else{
                        //Toast.makeText(applicationContext, "Login Failed!", Toast.LENGTH_SHORT).show()
                        println("Erro no login");
                        callback(false)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace();
                    callback(false)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
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
            login(username.text.toString(), password.text.toString()) { if (it) navigateToHome() }


            register.setOnClickListener {
                navigateToRegister()
            }
        }
    }
}