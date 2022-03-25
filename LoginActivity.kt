package com.example.myinstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Check if there's a user logged in
        // If there is, take them to the mainActivity
        if (ParseUser.getCurrentUser() != null){
           goToMainActivity()
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener({
            val username = findViewById<EditText>(R.id.editUsername).text.toString()
            val password = findViewById<EditText>(R.id.editPassword).text.toString()
            lUser(username, password)
        })
        findViewById<Button>(R.id.btnSignUp).setOnClickListener(
            {
                val username = findViewById<EditText>(R.id.editUsername).text.toString()
                val password = findViewById<EditText>(R.id.editPassword).text.toString()
                signUpUser(username, password)
            })
    }


    private fun signUpUser(username: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // User has successfully created a new account

                // TODO: Navigate the user to the MainActivity
                // TODO: Show a toast to indicate user successfully signed up for an account
                Toast.makeText(this, "User successfully Signed Up", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Show a toast to tell user sign up was not successful
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
                e.printStackTrace()
                Toast.makeText(this, "Error Signing Up", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun lUser(username: String, password: String) {
    ParseUser.logInInBackground(username, password, ({user, e ->
        if (user != null){
            // Hooray! The user is logged in
            Log.i(TAG, "Successfully logged in user")
            goToMainActivity()
        } else {
            // Signup failed. Look at the ParseException to see what happened.
            e.printStackTrace()
            Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
        }
    }))

    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java )
        startActivity(intent)
        finish()


    }




    companion object {
       const val TAG = "LoginActivity"
    }
