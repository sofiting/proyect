package com.example.taxast_taxi.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.taxast_taxi.R
import com.example.taxast_taxi.RentActivity
import com.example.taxast_taxi.databinding.ActivityConfirmRentBinding

class ConfirmRent : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmRentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmRentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val rent = intent.getParcelableExtra<RentActivity>("rent")

        if (rent != null) {
            binding.nameR.text = rent.name
            binding.dniR.text = rent.dni
            binding.locationR.text = rent.picklocationrent
            binding.dateR.text = rent.selectDate
            binding.timeR.text = rent.pickTimeRent
            binding.returnR.text = rent.returnDate
            binding.payR.text = rent.age


            binding.confirmPayR.setOnClickListener {
                val intent = Intent(this, SuccessPayment::class.java)
                startActivity(intent)
            }
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
                // Manejar la selección de la opción 1
                // Puedes realizar acciones específicas aquí
                return true
            }

            R.id.action_settings -> {
                // Manejar la selección de la opción 2
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}
