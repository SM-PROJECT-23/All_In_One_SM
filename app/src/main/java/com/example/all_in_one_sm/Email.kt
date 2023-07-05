package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.util.*

class Email : AppCompatActivity() {

    private lateinit var OldPass: TextInputEditText
    private lateinit var goReset: Button

    private fun navigateToResetPass() {
        val intent = Intent(this, ForgotPassword::class.java)
        startActivity(intent)
    }

    private fun getUsernameFromAPI(): String? {
        var username: String? = null
        val (_, response, result) = Fuel.get("https://my-json-server.typicode.com/a41792/FakeApi/people")
            .responseString()

        if (response.isSuccessful) {
            val data = result.get()
            val jsonArray = JSONArray(data)

            if (jsonArray.length() > 0) {
                val usernames = mutableListOf<String>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val nameUser = jsonObject.getString("username")
                    usernames.add(nameUser)
                }

                val random = Random()
                val randomIndex = random.nextInt(usernames.size)
                username = usernames[randomIndex]
            }
        } else {
            println("Email not found!")
        }

        return username
    }

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking<Unit> {
        super.onCreate(savedInstanceState)
        supportActionBar?.title =
            Html.fromHtml("<font color=\"white\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.email)

        // Initialize views
        OldPass = findViewById(R.id.usernameEditText)
        goReset = findViewById(R.id.login_button1)

        // Call the function to set the random username to the EditText view using a coroutine
        launch(Dispatchers.IO) {
            val username = getUsernameFromAPI()

            withContext(Dispatchers.Main) {
                if (username != null) {
                    // Set the retrieved username to the TextInputEditText view with ID "usernameEditText"
                    OldPass.setText(username)
                } else {
                    // Handle the case when no username is retrieved
                }
            }
        }

        goReset.setOnClickListener {
            navigateToResetPass()
        }
    }
}