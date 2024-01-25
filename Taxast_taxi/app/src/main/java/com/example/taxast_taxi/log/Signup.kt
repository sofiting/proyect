package com.example.taxast_taxi.log

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taxast_taxi.databinding.ActivitySignupBinding
import com.example.taxast_taxi.home.Home
import com.example.taxast_taxi.util.Utils

class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener {
            val nameSign = binding.userName.text.toString()
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            val dniSign = binding.dnisignup.text.toString()

            if (isInputValid(nameSign, email, pass, dniSign)) {
                val intent = Intent(this, Home::class.java)
                intent.putExtra("name", nameSign)
                intent.putExtra("dni",dniSign)
                startActivity(intent)
            }
        }

        binding.login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun isInputValid(name: String, email: String, pass: String, dni: String): Boolean {
        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Utils.showToast(this, "Any field can be empty")
            return false
        }

        if (name.length > 30) {
            Utils.showToast(this, "User name can be longer than 30 characters")
            return false
        }

        if (!Utils.validateDNI(dni)) {
            Utils.showToast(this, "DNI isn't valid")
            return false
        }

        if (!email.contains("@") || !email.contains(".")) {
            Utils.showToast(this, "Email is not valid")
            return false
        }

        if (pass.length !in 4..8) {
            Utils.showToast(this, "Password isn't valid (4-8 characters)")
            return false
        }

        return true
    }
}
