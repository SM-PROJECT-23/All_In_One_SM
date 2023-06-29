package com.example.all_in_one_sm
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.text.Html
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class loginAndRegister : AppCompatActivity() {

    private lateinit var logo : Image
    private lateinit var loginB : Button
    private lateinit var registerB : Button
    private lateinit var titulo: TextView

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterPage::class.java)
        startActivity(intent)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        /*val actionBar: ActionBar? = supportActionBar
        actionBar.hide()*/
        supportActionBar?.title = Html.fromHtml("<font color=\"white\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.login_and_register)

        // Initialize views
        loginB = findViewById(R.id.loginButton)
        registerB = findViewById(R.id.registoB)
        titulo = findViewById(R.id.all)

        registerB.setOnClickListener {
            navigateToLogin()
        }

        loginB.setOnClickListener {
            navigateToRegister()
        }
    }
}