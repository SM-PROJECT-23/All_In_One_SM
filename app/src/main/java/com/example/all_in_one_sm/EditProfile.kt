package com.example.all_in_one_sm
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EditProfile : AppCompatActivity() {
    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var phoneEditText: TextInputEditText
    private lateinit var countryEditText: TextInputEditText
    private lateinit var cityEditText: TextInputEditText
    private lateinit var oldPasswordEditText: TextInputEditText
    private lateinit var newPasswordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText

    private fun navigateToProfile() {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity to prevent navigating back to the login page
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        countryEditText = findViewById(R.id.countryEditText)
        cityEditText = findViewById(R.id.cityEditText)
        oldPasswordEditText = findViewById(R.id.oldpasswordEditText)
        newPasswordEditText = findViewById(R.id.newpasswordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)

        val cancelButton: Button = findViewById(R.id.CancelButton)
        val saveButton: Button = findViewById(R.id.SaveButton)

        // Add click listeners to buttons
        cancelButton.setOnClickListener {
            // Handle cancel button click
            navigateToProfile()
        }

        saveButton.setOnClickListener {
            // Handle save button click
            navigateToProfile()
        }
    }
}
