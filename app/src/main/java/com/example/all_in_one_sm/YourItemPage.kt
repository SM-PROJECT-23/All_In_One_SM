package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class YourItemPage: AppCompatActivity() {

    private lateinit var remove1: Button
    private lateinit var edit1: Button

    private fun navigateToAddItemPage() {
        val intent = Intent(this, AddItemFragment::class.java) // Replace MainActivity with the appropriate activity class
        startActivity(intent)
    }

    private fun navigateToEditItemPage() {
        val intent = Intent(this, EditItemPage::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.youritempage)

        remove1 = findViewById(R.id.removebuttonYourPage)
        edit1 = findViewById(R.id.editbuttonYourPage)

        remove1.setOnClickListener {
            navigateToAddItemPage()
        }

        edit1.setOnClickListener {
            navigateToEditItemPage()
        }
    }
}