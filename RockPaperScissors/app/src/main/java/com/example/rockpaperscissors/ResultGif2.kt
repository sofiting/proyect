package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultGif2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_gif2)

        // Obtiene los nombres de los jugadores y el resultado de la ronda del juego
        val name1 = intent.getStringExtra("name1")
        val name2 = intent.getStringExtra("name2")
        var roundResult = intent.getIntExtra("optionResult",0)


        var finalMessage :TextView = findViewById(R.id.resulTxt)
        var again2 : Button = findViewById(R.id.ppAgain)
        var finish2 : Button = findViewById(R.id.ppFinish)

        // Asigna un mensaje de resultado según el resultado de la ronda
        when (roundResult) {
            1 -> finalMessage.text = "$name1 wins ⸜(｡˃ ᵕ ˂ )⸝♡"
            -1 -> finalMessage.text = "$name2 wins ⸜(｡˃ ᵕ ˂ )⸝♡"
            0 -> finalMessage.text = "It's a tie ٩(˘◡˘)۶"
        }

        // Configura acciones cuando se hace clic en los botones
        again2.setOnClickListener {
            // Inicia la actividad para jugar entre los mismos dos jugadores nuevamente
            val intent = Intent(this, PlayerWithPlayer::class.java)
            //Recupera los nombres de los jugadores
            intent.putExtra("n1", name1)
            intent.putExtra("n2", name2)
            startActivity(intent)
            finish()
        }

        // Reinicia las puntuaciones y regresa a la actividad principal
        finish2.setOnClickListener {
            ScoreManager2.player1Score = 0
            ScoreManager2.player2Score = 0

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}