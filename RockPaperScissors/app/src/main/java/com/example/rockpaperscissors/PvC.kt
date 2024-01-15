package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PvC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pv_c)

        var name : EditText = findViewById(R.id.name)
        var start : Button = findViewById(R.id.startPvC)

        // Configura un clic en el botón "start"
        start.setOnClickListener {
            if(name.text.isEmpty()){
                Toast.makeText(this,"name cannot be empty",Toast.LENGTH_SHORT).show()
            }else{
                // Regirige a la otra vista
                val playPC = Intent(this,PlayWithPC::class.java)
                playPC.putExtra("name", name.text.toString()) // Pasar el nombre a través del Intent
                startActivity(playPC)
            }
        }
    }
}