package com.example.all_in_one_sm
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

/*interface ApiService {
    @PUT("users/{userId}/password")
    suspend fun changePassword(
        @Path("userId") userId: String,
        @Body newPassword: NewPasswordRequest
    ): Response<Unit>
}*/


data class UserProfile(
    val name: String,
    val username: String,
    val email: String,
    val country: String,
    val password: String

    )

class Profile : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var countryTextView: TextView
    private lateinit var passwordTextView: TextView

    /*  private fun loginUser() {
        // Create a UserProfile object with the provided login information
        val userProfile = UserProfile("xana", "x", "xana@ipb.pt", "pt", "abc")

        // Create a Gson instance to convert the UserProfile object to JSON
        val gson = Gson()
        val json = gson.toJson(userProfile)

        // Create an OkHttpClient instance to send the HTTP request
        val client = OkHttpClient()

        // Set the media type for the request body
        val mediaType = "application/json".toMediaType()

        // Create a request body with the JSON payload
        val requestBody = json.toRequestBody(mediaType)

        // Create a POST request with the API endpoint URL and request body
        val request = Request.Builder()
            .url("https://my-json-server.typicode.com/a41792/SM3/register/")
            .post(requestBody)
            .build()

        // Execute the request and get the response
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Failed to login: " + response.code)
            }

            // Handle the response here
            // You can access the response body using response.body()?.string()
            // Extract relevant information from the response and perform necessary actions
        }
    }*/

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditProfile::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        nameTextView = findViewById(R.id.nameTextView)
        usernameTextView = findViewById(R.id.usernameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        countryTextView = findViewById(R.id.countryTextView)
        passwordTextView = findViewById(R.id.passwordTextView)

        val editButton: Button = findViewById(R.id.EditProfileButton)

        val PROFILE_API_URL = "https://my-json-server.typicode.com/a41792/SM3/users/{userId}"

        // Assuming you have the user's ID and the new password
        val userId = "user123"
        val newPassword = "newpassword"

        // Create a new instance of the user's profile with the updated password
        val updatedProfile = UserProfile("", "", "", "", newPassword)

        // Convert the updated profile to JSON
        val jsonBody = Gson().toJson(updatedProfile)

        // Make the API request to update the password
        /*Fuel.patch(PROFILE_API_URL.replace("{userId}", userId))
            .header("Content-Type", "application/json")
            .body(jsonBody)
            .response { _, response, result ->
                when (result) {
                    is Result.Success -> {
                        // Handle the successful response from the server
                        if (response.statusCode == 200) {
                            println("Password updated successfully")
                        } else {
                            println("Failed to update password. Server returned status code: ${response.statusCode}")
                        }
                    }
                    is Result.Failure -> {
                        // Handle the error case
                        println("Failed to update password: ${result.error.localizedMessage}")
                    }
                    else -> {}
                }

                editButton.setOnClickListener {
                    navigateToEditProfile()
                }
            }
    }
*/
    }
}