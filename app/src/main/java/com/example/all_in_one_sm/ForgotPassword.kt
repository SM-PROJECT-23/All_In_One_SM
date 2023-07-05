package com.example.all_in_one_sm
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.editprofile.*
import kotlinx.android.synthetic.main.register.*
import kotlinx.android.synthetic.main.register.confirmPasswordEditText
import java.util.*

data class Password(
    @SerializedName("username")
    var username: String?,
    @SerializedName("actpassword")
    var actP: String?,
    @SerializedName("newP")
    var newP: String?,
    @SerializedName("confP")
    var confP: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(actP)
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

    private lateinit var OldPass : TextInputEditText
    private lateinit var NewPass : TextInputEditText
    private lateinit var forgot : Button
    private lateinit var confPass: TextInputEditText

    private lateinit var currentUser: Password

    private fun navigateToLogin(updatedUser: Password) {
        val intent = Intent(this, LoginPage::class.java)
        intent.putExtra("updatedUser", updatedUser)
        startActivity(intent)
    }

    private fun updateUser(username: String, passact: String, newP: String, confP: String) {
        currentUser.username = username
        currentUser.actP = passact
        currentUser.newP = newP
        currentUser.confP = confP
    }

    private fun saveUserToPrefs(user: Password) {
        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("username", user.username)
        editor.putString("actpassword", user.actP)
        editor.putString("newP", user.newP)
        editor.putString("conf", user.confP)
        editor.apply()
    }

    fun updatePassword(email: String, newPassword: String): Boolean {

        val passwordStorage = mutableMapOf(
            "example@example.com" to "password"
            // Add more entries as needed
        )

        // Check if the email exists in the storage
        if (passwordStorage.containsKey(email)) {
            // Update the password
            passwordStorage[email] = newPassword
            return true // Password updated successfully
        }

        return false // Email not found or password not updated
    }

    private fun displayUserInfo(user: Password) {
        usernameEditText.setText(user.username)
        oldpasswordEditText.setText("")
        newpasswordEditText.setText("")
        confirmPasswordEditText.setText("")
    }

    private fun getUserData(username: String?): Password? {
        val userList = listOf(
            Password("ola", "", "", ""),
            Password("teste", "", "", ""),
            Password("xpto", "", "", "")
        )


        return userList.find { it.username == username }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        setContentView(R.layout.forgotpassword)

        val prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
        val savedUsername = prefs.getString("username", null)

        val scanner = Scanner(System.`in`)

        println("Forgot Password Page")
        print("Enter your email: ")
        val email = scanner.nextLine()

        print("Enter your new password: ")
        val newPassword = scanner.nextLine()

        val passwordUpdated = updatePassword(email, newPassword)

        if (passwordUpdated) {
            println("Password updated successfully!")
        } else {
            println("Failed to update the password. Email not found.")
        }

        getUserData(savedUsername)?.let { userData ->
            currentUser = userData
            displayUserInfo(currentUser)
        } ?: run {
            println("erro!")
        }

        // Initialize views
        OldPass = findViewById(R.id.usernameEditText)
        NewPass = findViewById(R.id.passwordNew)
        confPass = findViewById(R.id.passwordEditText)
        forgot = findViewById(R.id.reset_button)

        forgot.setOnClickListener {
            val enteredUsername = emailEditText.text.toString()
            val enteredOldPass = oldpasswordEditText.text.toString()
            val enteredNewPassword = newpasswordEditText.text.toString()
            val enteredConfirmPassword = confirmPasswordEditText.text.toString()

            // Perform validation if needed
            if (enteredUsername.isNotEmpty() && enteredNewPassword.isNotEmpty() && enteredConfirmPassword.isNotEmpty()) {
                // Check if the new password matches the confirmed password
                if (enteredNewPassword == enteredConfirmPassword) {
                    // Update the user information
                    updateUser(enteredUsername, enteredNewPassword, "", "")

                    // Create the updated user object
                    val updatedUser = Password(
                        savedUsername,
                        enteredNewPassword,
                        enteredNewPassword,
                        enteredConfirmPassword
                    )

                    // Save the updated user to preferences
                    saveUserToPrefs(updatedUser)

                    // Navigate to the Login page and pass the updated user object
                    navigateToLogin(updatedUser)
                } else {
                    println("New password and confirm password do not match!")
                }
            } else {
                println("Please fill in all the fields!")
            }
        }
    }
}