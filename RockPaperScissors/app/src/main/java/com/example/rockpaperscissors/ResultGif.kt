package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultGif : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_gif)


        var gif : ImageView = findViewById(R.id.gif)
        var message : TextView = findViewById(R.id.message)
        val again : Button = findViewById(R.id.ppAgain)
        val finish :Button = findViewById(R.id.ppFinish)

        //Recuperar los datos del intent anteriores
        val name = intent.getStringExtra("name")
        var result = intent.getIntExtra("gameResult",0)
        val resultMessage = intent.getStringExtra("resultMessage")

        // Asigna un GIF y un mensaje de resultado según el resultado del juego
        when (result) {
            1 -> gif.setImageResource(R.drawable.happy)
            -1 -> gif.setImageResource(R.drawable.cry)
            0 -> gif.setImageResource(R.drawable.hands)
        }

        message.text = resultMessage ?: ""  // si es nulo muestra cadena vacío, si no es nulo muestra contenido

        // Configura acciones cuando se hace clic en los botones
        again.setOnClickListener {

            // Inicia la actividad de juego contra la computadora nuevamente
            val intent = Intent(this, PlayWithPC::class.java)
            // Pasar el nombre de esta manera recupera el nombre
            intent.putExtra("name", name)
            startActivity(intent)
            finish()
        }

        // Reinicia las puntuaciones y regresa a la actividad principal
        finish.setOnClickListener {
            ScoreManager.playerScore = 0
            ScoreManager.pcScore = 0

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }





    }
}