package com.example.all_in_one_sm
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class EditProfile : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etCountry: EditText
    private lateinit var etCity: EditText
    private lateinit var etOldPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmP: EditText
    // Add references to the rest of the EditText fields

    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button

    private fun navigateToProfile() {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity to prevent navigating back to the login page
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile)

        // Find views by their IDs
        etName = findViewById(R.id.nameTextInput)
        etEmail = findViewById(R.id.emailEditEditText)
        etPhoneNumber = findViewById(R.id.phoneEditText)
        etCountry = findViewById(R.id.countryEditText)
        etCity = findViewById(R.id.cityEditText)
        etOldPassword = findViewById(R.id.oldpasswordEditText)
        etNewPassword = findViewById(R.id.newpasswordEditText)
        etConfirmP = findViewById(R.id.confirmPasswordEditText)

        btnCancel = findViewById(R.id.CancelButton)
        btnSave = findViewById(R.id.SaveButton)

        // Set click listeners for the buttons
        btnCancel.setOnClickListener {
            // Handle cancel button click event
            navigateToProfile()
            finish() // Close the activity
        }

        btnSave.setOnClickListener {
            // Handle save button click event
            saveProfile() // Call a function to save the profile information
            navigateToProfile()
        }

        val countrySpinner = findViewById<Spinner>(R.id.countrySpinner)
        val countries = arrayOf("Portugal", "France", "Engand") // Replace with your country options

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = adapter

    }

    private fun saveProfile() {
        // Retrieve values from the EditText fields
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val city = etCity.text.toString()
        val country = etCountry.text.toString()
        val password = etNewPassword.text.toString()

        // Close the activity
        finish()
    }
}