package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PlayWithPC : AppCompatActivity() {

    private var name1: String = ""
    // Variable que controla el click del jugador
    private var playerClicked: Boolean = false

    // Variables globales
    var gameResult: Int = 0
    var resultMessage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_with_pc)

        // Obtiene el nombre del jugador del intent anterior
        name1 = intent.getStringExtra("name") ?: ""
        val name2: TextView = findViewById(R.id.name)
        name2.text = name1

        var myResult: ImageView = findViewById(R.id.myResul)
        var pcResult: ImageView = findViewById(R.id.pcResult)

        var rChoose: ImageView = findViewById(R.id.rChoose)
        var sChoose: ImageView = findViewById(R.id.sChoose)
        var pChoose: ImageView = findViewById(R.id.pChoose)

        // Define acciones cuando el jugador elige una opción
        rChoose.setOnClickListener {

            if (!playerClicked) {

                playerClicked = true

                val computerChoice = getRandomImage()

                // Para poner una imagen en la aplicación
                myResult.setImageResource(R.drawable.rock)
                pcResult.setImageResource(getImageResource(computerChoice))

                gameResult = determineWinner(0, computerChoice)
                updateScore(gameResult)
                updateResultText(gameResult)
                showGif()

            }
        }

        sChoose.setOnClickListener {

            if (!playerClicked) {

                playerClicked = true

                val computerChoice = getRandomImage()

                myResult.setImageResource(R.drawable.scissors)
                pcResult.setImageResource(getImageResource(computerChoice))

                gameResult = determineWinner(1, computerChoice)
                updateScore(gameResult)
                updateResultText(gameResult)
                showGif()

            }
        }

        pChoose.setOnClickListener {

            if (!playerClicked) {

                playerClicked = true

                val computerChoice = getRandomImage()

                myResult.setImageResource(R.drawable.paper)
                pcResult.setImageResource(getImageResource(computerChoice))

                gameResult = determineWinner(2, computerChoice)
                updateScore(gameResult)
                updateResultText(gameResult)
                showGif()

            }
        }
    }

    private fun getRandomImage(): Int {
        val randomIndex = (Math.random() * 3).toInt()
        return randomIndex
    }

    // Devuelve el recurso de imagen según la elección (0: piedra, 1: tijeras, 2: papel) para lanzar una imagen aleatoria
    private fun getImageResource(choice: Int): Int {
        return when (choice) {
            0 -> R.drawable.rock
            1 -> R.drawable.scissors
            2 -> R.drawable.paper
            else -> R.drawable.inter
        }
    }

    // Determina al ganador del juego y devuelve el resultado (0: empate, 1: jugador gana, -1: computadora gana)
    private fun determineWinner(playerChoice: Int, computerChoice: Int): Int {
        // 0: piedra, 1: tijeras, 2: papel
        if (playerChoice == computerChoice) {
            return 0 // empate
        }
        if ((playerChoice == 0 && computerChoice == 1) ||
            (playerChoice == 1 && computerChoice == 2) ||
            (playerChoice == 2 && computerChoice == 0)) {
            return 1 // humana gana
        }
        return -1 // pc gana
    }

    // Actualiza las puntuaciones de los jugadores y muestra las puntuaciones en las vistas
    private fun updateScore(result: Int) {
        when (result) {
            1 -> ScoreManager.playerScore++
            -1 -> ScoreManager.pcScore++
        }
        // Obtener las vistas de puntuación
        val personScoreText = findViewById<TextView>(R.id.personScore)
        val itScoreText = findViewById<TextView>(R.id.itScore)

        // Actualizar las puntuaciones en las vistas
        personScoreText.text = ScoreManager.playerScore.toString()
        itScoreText.text = ScoreManager.pcScore.toString()
    }

    // Actualiza el mensaje de resultado según el resultado del juego
    private fun updateResultText(result: Int) {
        val resultText = findViewById<TextView>(R.id.resulMessage)

        when (result) {
            1 -> {
                resultText.text = "You Win ⸜(｡˃ ᵕ ˂ )⸝♡"
                // Asigna el mensaje de resultado , para poder pasar a la otra actividad
                resultMessage = "You Win ⸜(｡˃ ᵕ ˂ )⸝♡"
            }
            -1 -> {
                resultText.text = "You Lose (ㅠ﹏ㅠ)"
                resultMessage = "You Lose (ㅠ﹏ㅠ)"
            }
            else -> {
                resultText.text = "It's a Tie! ✧( •⌄• )◞◟( •⌄• )✧"
                resultMessage = "It's a Tie! ✧( •⌄• )◞◟( •⌄• )✧"
            }
        }
    }

    // Retrasar el tiempo y redirigir a otra actividad para mostrar un GIF
    private fun showGif() {
        val handler = Handler()
        handler.postDelayed({
            // Redirigir a la otra actividad
            val intent = Intent(this, ResultGif::class.java) // Reemplaza OtraActividad::class.java con la clase de la otra actividad
            intent.putExtra("gameResult", gameResult)
            intent.putExtra("resultMessage", resultMessage)
            intent.putExtra("name", name1)
            startActivity(intent)
            finish()
        }, 1800) // en milisegundos
    }
}

// Crear una clase para almacenar puntos
object ScoreManager {
    var playerScore: Int = 0
    var pcScore: Int = 0
}
