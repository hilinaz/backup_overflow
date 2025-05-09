package com.example.overflow_try

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.overflow_try.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            
            if (validateInput(email, password)) {
                // TODO: Implement login logic
                Toast.makeText(this, "Login clicked", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerButton.setOnClickListener {
            // TODO: Navigate to registration screen
            Toast.makeText(this, "Register clicked", Toast.LENGTH_SHORT).show()
        }

        binding.forgotPasswordButton.setOnClickListener {
            // TODO: Navigate to forgot password screen
            Toast.makeText(this, "Forgot password clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.emailEditText.error = "Email is required"
            return false
        }
        if (password.isEmpty()) {
            binding.passwordEditText.error = "Password is required"
            return false
        }
        return true
    }
} 