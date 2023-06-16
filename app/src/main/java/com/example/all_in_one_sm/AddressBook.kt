package com.example.all_in_one_sm

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class AddressBook : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_book)

        val countries = arrayOf("USA", "Canada", "UK", "PT", "SPN", "IT") // Add more countries as needed
        val spinner: Spinner = findViewById(R.id.countrySpinner)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries)
        spinner.adapter = adapter

        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            // Retrieve data from EditTexts and Spinner
            val name = findViewById<EditText>(R.id.editTextName).text.toString()
            val country = spinner.selectedItem.toString()
            val state = findViewById<EditText>(R.id.editTextState).text.toString()
            val city = findViewById<EditText>(R.id.editTextCity).text.toString()
            val postalCode = findViewById<EditText>(R.id.editTextPostalCode).text.toString()
            val address = findViewById<EditText>(R.id.editTextAddress).text.toString()
            val phoneNumber = findViewById<EditText>(R.id.editTextPhoneNumber).text.toString()

            //Falta guardar o address na api
        }
    }
}
