package com.example.intellicateradmin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.intellicateradmin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        if (isLoggedIn()) {
            startMainActivity()
            return
        }

        binding.LoginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.password1.text.toString()

            if (username == "Admin123" && password == "Admin@123") {
                // Credentials are correct, navigate to MainActivity
                setLoggedIn(true)
                // Navigate to MainActivity
                startMainActivity()

            } else {
                // Incorrect credentials, show a toast message
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun setLoggedIn(loggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", loggedIn).apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Finish the LoginActivity to prevent going back when pressing back button
    }
}