package com.example.taxast_taxi.log

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taxast_taxi.databinding.ActivityLoginBinding
import com.example.taxast_taxi.home.Home
import com.example.taxast_taxi.util.Utils

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        binding.login.setOnClickListener {
            val name = binding.name.text.toString()
            val pass = binding.password.text.toString()
            val dni = binding.dniLog.text.toString()

            if (isLoginValid(name, pass, dni)) {
                val intent = Intent(this, Home::class.java)
                intent.putExtra("name", name)
                intent.putExtra("dni",dni)
                startActivity(intent)
            }
        }
    }

    private fun isLoginValid(name: String, pass: String, dni: String): Boolean {
        if (name.isEmpty() || pass.isEmpty() || dni.isEmpty()) {
            Utils.showToast(this, "No field can be empty")
            return false
        }

        if (name.length > 30) {
            Utils.showToast(this, "User name is too long")
            return false
        }

        if (pass.length !in 4..8) {
            Utils.showToast(this, "Password isn't valid (4-8 characters)")
            return false
        }

        // si conecto bbdd este campo no es necesario estar aquí
        if (!Utils.validateDNI(dni)) {
            Utils.showToast(this, "DNI isn't valid")
            return false
        }

        return true
    }


}
