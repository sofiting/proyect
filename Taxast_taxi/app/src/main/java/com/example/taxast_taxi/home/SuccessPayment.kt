package com.example.taxast_taxi.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import com.example.taxast_taxi.R
import com.example.taxast_taxi.account.Account
import com.example.taxast_taxi.databinding.ActivityConfirmPaymentBinding
import com.example.taxast_taxi.databinding.ActivitySuccessPaymentBinding
import com.example.taxast_taxi.history.History
import com.example.taxast_taxi.menu.Favourite
import com.example.taxast_taxi.menu.Setting

class SuccessPayment : AppCompatActivity() {

    private lateinit var binding:ActivitySuccessPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_payment)

        binding = ActivitySuccessPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setupBottomNavigationView()

        val name = intent.getStringExtra("name");
        val dni = intent.getStringExtra("dni")
        Log.d("final",name.toString())
        Log.d("final",dni.toString())



        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Home::class.java)
            intent.putExtra("name", name)
            intent.putExtra("dni", dni)
            startActivity(intent)
            finish()
        }, 2000)


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
                   // startActivity(Intent(this, Home::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.history -> {
                    // Navigate to the HistoryActivity
                    startActivity(Intent(this, History::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.account -> {
                    // Navigate to the AccountActivity
                    startActivity(Intent(this, Account::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
}