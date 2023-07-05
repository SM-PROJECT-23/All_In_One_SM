package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import org.json.JSONArray

class Email : AppCompatActivity() {

    private lateinit var goReset: Button

    private fun navigateToResetPass() {
        val intent = Intent(this, ForgotPassword::class.java)
        startActivity(intent)
    }

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

        // Call the function to set the random username to the EditText view using a coroutine
        GlobalScope.launch(Dispatchers.IO) {
            val usernames = getUsernameFromAPI()

            withContext(Dispatchers.Main) {
                if (usernames.isNotEmpty()) {
                    // Display the usernames in a dropdown list
                    val spinner: Spinner = findViewById(R.id.userSpinner)
                    val adapter = ArrayAdapter(this@Email, android.R.layout.simple_spinner_item, usernames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter

                    // Set a listener to handle user selection
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                            val selectedUsername = parent.getItemAtPosition(position) as String
                            //OldPass.setText(selectedUsername)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // Handle the case when no username is selected
                        }
                    }
                } else {
                    // Handle the case when no usernames are retrieved
                }
            }
        }

        goReset.setOnClickListener {
            navigateToResetPass()
        }
    }
}