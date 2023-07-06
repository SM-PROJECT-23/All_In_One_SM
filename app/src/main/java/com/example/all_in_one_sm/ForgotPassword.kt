package com.example.all_in_one_sm
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.editprofile.*
import kotlinx.android.synthetic.main.register.*
import kotlinx.android.synthetic.main.register.confirmPasswordEditText
import java.util.*

data class Password(
    @SerializedName("email")
    var email: String?,
    @SerializedName("newP")
    var newP: String?,
    @SerializedName("confP")
    var confP: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(newP)
        parcel.writeString(confP)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

class ForgotPassword : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var newPassEditText: TextInputEditText
    private lateinit var confPassEditText: TextInputEditText
    private lateinit var resetButton: Button
    private lateinit var currentUser: Password

    private fun navigateToLogin() {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

    private fun saveUserToPrefs(email: String, newP: String) {
        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("email", email)
        editor.putString("newP", newP)
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgotpassword)

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText)
        newPassEditText = findViewById(R.id.passwordNew)
        confPassEditText = findViewById(R.id.passwordEditText)
        resetButton = findViewById(R.id.reset_button)

        resetButton.setOnClickListener {
            val enteredEmail = emailEditText.text.toString()
            val enteredNewPassword = newPassEditText.text.toString()
            val enteredConfirmPassword = confPassEditText.text.toString()

            // Perform validation if needed
            if (enteredEmail.isNotEmpty() && enteredNewPassword.isNotEmpty() && enteredConfirmPassword.isNotEmpty()) {
                // Check if the new password matches the confirmed password
                if (enteredNewPassword == enteredConfirmPassword) {
                    // Save the updated information to preferences
                    saveUserToPrefs(enteredEmail, enteredNewPassword)

                    // Navigate to the Login page
                    navigateToLogin()
                    Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "New password and confirm password do not match!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all the fields!", Toast.LENGTH_LONG).show()
            }
        }
    }
}