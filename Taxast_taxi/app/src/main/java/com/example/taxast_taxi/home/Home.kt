package com.example.taxast_taxi.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.taxast_taxi.R
import com.example.taxast_taxi.account.Account
import com.example.taxast_taxi.databinding.ActivityHomeBinding
import com.example.taxast_taxi.history.History
import com.example.taxast_taxi.menu.Favourite
import com.example.taxast_taxi.menu.Setting


class Home : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //////////////////////////Tingmei Huang///////////////////////////////////

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Establece la Toolbar como la ActionBar de la actividad
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setupBottomNavigationView()

        val name = intent.getStringExtra("name");
        binding.username.setText("Hi ! "+ name)
       // binding.username.setText("Welcome to Taxast ! ")
        val dni = intent.getStringExtra("dni")

        Log.d("sara",name.toString())
        Log.d("sara",dni.toString())

        binding.taxi.setOnClickListener {
            val intent = Intent(this, Ridetaxi::class.java)
            intent.putExtra("name",name)
            intent.putExtra("dni",dni)
            startActivity(intent)
        }

        binding.rent.setOnClickListener {
            val intent = Intent(this, Rentcar::class.java)
            intent.putExtra("name",name)
            intent.putExtra("dni",dni)
            startActivity(intent)
        }
    }

    // Inflar el menú
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
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
                    // You are already in the Home activity, so no need to navigate
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
