package com.example.taxast_taxi.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class Utils : AppCompatActivity() {
    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun validateDNI(dni: String): Boolean {
            // Verifica que el DNI tenga 8 dígitos seguidos de una letra
            val dniRegex = Regex("^\\d{8}[A-Za-z]$")

            return dni.matches(dniRegex)
        }

    }
}