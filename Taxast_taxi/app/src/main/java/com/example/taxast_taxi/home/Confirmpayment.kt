package com.example.taxast_taxi.home

import Travel
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.taxast_taxi.R
import com.example.taxast_taxi.databinding.ActivityConfirmPaymentBinding
import com.example.taxast_taxi.menu.Favourite
import com.example.taxast_taxi.menu.Setting

class Confirmpayment : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val pickd = intent.getStringExtra("pickd")
        binding.pickd.text = pickd.toString()

        val name = intent.getStringExtra("name");
        val dni = intent.getStringExtra("dni")

        val travel = intent.getParcelableExtra<Travel>("travel")

        if (travel != null) {
            binding.confirmName.text = travel.name
            binding.confirmDni.text = travel.dni
            binding.round.text = if (travel.isRoundTrip) "Round trip" else "One way trip"
            binding.placeFrom.text = travel.originTrip
            binding.placeTo.text = travel.destiTrip
            binding.pass.text = travel.numberPassenger
            binding.pickd.text = travel.pickDate
            binding.pickt.text = travel.pickTime
            binding.backd.text = travel.backDate ?: ""
            binding.backdt.text = travel.backTime ?: ""

            // Calcular el pago
            val numberPass = travel.numberPassenger.toIntOrNull() ?: 0 // Convierte a entero o usa 0 si no se puede convertir
            // Mostrar el pago en un TextView
            binding.pay.text = "${(numberPass * 30)} Euros"


            binding.confirmPay.setOnClickListener {
                 val intent = Intent(this,SuccessPayment::class.java)
                intent.putExtra("name",name)
                intent.putExtra("dni",dni)
                startActivity(intent)
            }
        }
    }
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
}
