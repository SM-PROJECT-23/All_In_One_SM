package com.example.all_in_one_sm
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.OkHttpClient as OkHttpClient1

data class UserModel(
    val name: String ="",
    @SerializedName("name")
    val username:String?="",
    @SerializedName("username")
    val email:String?="",
    @SerializedName("email")
   /* var phoneNumber:Int? = 0,
    @SerializedName("phoneNumber")*/
    val country:String?="",
    @SerializedName("country")
    val city:String?="",
    @SerializedName("city")
    val password:String?="",
    @SerializedName("password")
    val confirmPassword:String?="",
)

class RegisterPage : AppCompatActivity() {
    private lateinit var id: EditText
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

    private fun registerUser(user: UserModel) {

        val url = "http://192.168.1.104:3000/register"

        val requestBody = """
        {
            "name": "${user.name}",
            "username": "${user.username}",
            "email": "${user.email}",
            "city": "${user.city}",
            "country": "${user.country}",
            "password": "${user.password}",
            "confirm pass": "${user.confirmPassword}"   
        }
    """.trimIndent()

        val mediaType = "application/json; charset=utf-8".toMediaType()

        CoroutineScope(IO).launch {
            val request = Request.Builder()
                .url(url)
                .post(requestBody.toRequestBody(mediaType))
                .build()

            val client = OkHttpClient1()
            val response = client.newCall(request).execute()

            val responseCode = response.code

            val responseBody = response.body?.string()

            withContext(Dispatchers.Main) {
                if (responseCode == 201) {
                    println("Registration Successful!")
                } else {
                    println("Registration Failed. Response Code: $responseCode")
                }

                println("Response Body: $responseBody")
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
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
      //  phone = findViewById(R.id.phoneEditText)
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
                    name.text.toString(),
                    username.text.toString(),
                    email.text.toString(),
                  //  123098456,
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

