package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import org.json.JSONArray

class Email : AppCompatActivity() {

    private lateinit var goReset: Button
    private lateinit var usernameInput: TextInputEditText

    private suspend fun getUsernameFromAPI(): List<String> {
        return withContext(Dispatchers.IO) {
            val usernames = mutableListOf<String>()
            val (_, response, result) = Fuel.get("https://my-json-server.typicode.com/a41792/FakeApi/people")
                .responseString()

            if (response.isSuccessful) {
                val data = result.get()
                val jsonArray = JSONArray(data)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val nameUser = jsonObject.getString("username")
                    usernames.add(nameUser)
                }
            } else {
                println("Email not found!")
            }

            usernames
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title =
            Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.email)

        // Initialize views
        goReset = findViewById(R.id.login_button1)
        usernameInput = findViewById(R.id.usernameEditText)

        goReset.setOnClickListener {
            val enteredUsername = usernameInput.text.toString()

            // Use coroutine to handle the network operation
            GlobalScope.launch(Dispatchers.IO) {
                val usernames = getUsernameFromAPI()

                withContext(Dispatchers.Main) {
                    if (usernames.contains(enteredUsername)) {
                        // If the username is found in the list, navigate to the ForgotPassword page
                        val intent = Intent(this@Email, ForgotPassword::class.java)
                        startActivity(intent)
                    } else {
                        // If the username is not found, show a toast message
                        Toast.makeText(this@Email, "Username not found!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
