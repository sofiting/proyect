package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PlayerVsPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_vs_player)

        // Encuentra las vistas de los campos de texto y el botón en el diseño de la actividad
        var n1 : EditText = findViewById(R.id.name1)
        var n2 : EditText = findViewById(R.id.name2)
        var start : Button = findViewById(R.id.startPvP)

        // Configura un clic en el botón "start"
        start.setOnClickListener {
            if(n1.text.isEmpty()|| n2.text.isEmpty()) {
                // Muestra un mensaje Toast si los campos están vacíos
                Toast.makeText(this, "names cannot be empty", Toast.LENGTH_SHORT).show()
            }else{
                // Si los campos no están vacíos, crea un intent y pasa los nombres como datos extras
                val intent = Intent(this,PlayerWithPlayer::class.java)
                intent.putExtra("n1",n1.text.toString())
                intent.putExtra("n2",n2.text.toString())

                // Inicia la actividad del juego con los nombres de los jugadores
                startActivity(intent)
            }
        }
    }
}