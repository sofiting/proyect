package com.example.taxast_taxi.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.taxast_taxi.R
import com.example.taxast_taxi.account.Account
import com.example.taxast_taxi.databinding.ActivityHistoryBinding
import com.example.taxast_taxi.databinding.ActivitySuccessPaymentBinding
import com.example.taxast_taxi.home.Home
import com.example.taxast_taxi.menu.Favourite
import com.example.taxast_taxi.menu.Setting

class History : AppCompatActivity() {

    private lateinit var binding:ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setupBottomNavigationView()
    }
    // Manejar las selecciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                startActivity(Intent(this, Favourite::class.java))
                return true
            }
            R.id.action_settings -> {
                startActivity(Intent(this, Setting::class.java))

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun setupBottomNavigationView() {
        val bottomNavigationView = binding.menu1

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, Home::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.history -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.account -> {
                    startActivity(Intent(this, Account::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
}