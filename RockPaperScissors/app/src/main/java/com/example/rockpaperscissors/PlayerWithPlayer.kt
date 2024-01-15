package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PlayerWithPlayer : AppCompatActivity() {
    // Variables globales
    private val imageArray = arrayOf(
        R.drawable.rock,
        R.drawable.paper,
        R.drawable.scissors
    )

    private var player1Choice: Int = -1
    private var player2Choice: Int = -1

    private var name1: String = ""
    private var name2: String = ""

    private var player1Clicked: Boolean = false
    private var player2Clicked: Boolean = false


    var optionResult = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_with_player)

        // Obtiene los nombres de los jugadores de intent anterior
        name1 = intent.getStringExtra("n1") ?: ""
        name2 = intent.getStringExtra("n2") ?: ""

        // Muestra los nombres de los jugadores en las vistas de texto
        val player1: TextView = findViewById(R.id.nameP1)
        player1.text = name1

        val player2: TextView = findViewById(R.id.nameP2)
        player2.text = name2

        // Configura clics en botones de elección para ambos jugadores
        val r1: Button = findViewById(R.id.rock1)
        val p1: Button = findViewById(R.id.paper1)
        val s1: Button = findViewById(R.id.scissors1)
        val r2: Button = findViewById(R.id.rock2)
        val p2: Button = findViewById(R.id.paper2)
        val s2: Button = findViewById(R.id.scissors2)

        val inter1: ImageView = findViewById(R.id.res1)
        val inter2: ImageView = findViewById(R.id.res2)


        // Define acciones cuando un jugador elige un botón
        r1.setOnClickListener {

                player1Clicked = true

                showResultOption(inter1, R.drawable.rock)
                player1Choice = 0 // 0 representa "rock" para el jugador 1
                determineWinnerAndScore()

                checkButtons()

        }

        p1.setOnClickListener {

                player1Clicked = true

                showResultOption(inter1, R.drawable.paper)
                player1Choice = 2 // 2 representa "paper" para el jugador 1
                determineWinnerAndScore()

                checkButtons()

        }

        s1.setOnClickListener {

                player1Clicked = true

                showResultOption(inter1, R.drawable.scissors)
                player1Choice = 1 // 1 representa "scissors" para el jugador 1
                determineWinnerAndScore()

                checkButtons()

        }

        r2.setOnClickListener {

                player2Clicked = true

                showResultOption(inter2, R.drawable.rock)
                player2Choice = 0 // 0 representa "rock" para el jugador 2
                determineWinnerAndScore()

                checkButtons()

        }

        p2.setOnClickListener {

                player2Clicked = true

                showResultOption(inter2, R.drawable.paper)
                player2Choice = 2 // 2 representa "paper" para el jugador 2
                determineWinnerAndScore()

                checkButtons()

        }

        s2.setOnClickListener {

                player2Clicked = true

                showResultOption(inter2, R.drawable.scissors)
                player2Choice = 1 // 1 representa "scissors" para el jugador 2
                determineWinnerAndScore()

                checkButtons()

        }
    }

    private fun showResultOption(inter: ImageView, image: Int) {
        val handler = Handler()

        // Crea una animación de desvanecimiento
        val fadeInAnimation = AlphaAnimation(0.0f, 1.0f)
        fadeInAnimation.duration = 200 // Duración en milisegundos

        // Establece un escuchador de animación
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // La animación ha comenzado
            }

            override fun onAnimationEnd(animation: Animation?) {
                // La animación ha terminado, cambia la imagen después del retraso
                val randomImage = imageArray[(Math.random() * imageArray.size).toInt()]
                inter.setImageResource(randomImage)
                inter.startAnimation(fadeInAnimation)

                handler.postDelayed({
                    inter.clearAnimation() // Detiene la animación después de 2 segundos
                    inter.setImageResource(image) // Cambia la imagen a la selección del jugador
                }, 1000)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // La animación se repite
            }
        })

        // Inicia la animación
        inter.startAnimation(fadeInAnimation)
    }

    // Determina el ganador y actualiza las puntuaciones
    // se inicializan en -1 en el inicio, y solo se actualizan cuando los jugadores hacen sus elecciones de esta manera consigo elecciones de ambos jugadores
    private fun determineWinnerAndScore() {

        if (player1Choice != -1 && player2Choice != -1) {
            optionResult = determineWinner(player1Choice, player2Choice)
            updateScore(optionResult)
        }
    }

    //toma las elecciones de los dos jugadores como argumentos (0 para piedra, 1 para tijeras y 2 para papel)
    // y determina el resultado del juego.
    private fun determineWinner(player1: Int, player2: Int): Int {
        // 0: piedra, 1: tijeras, 2: papel
        val result = when {
            player1 == player2 -> 0 // Empate
            (player1 == 0 && player2 == 1) ||
                    (player1 == 1 && player2 == 2) ||
                    (player1 == 2 && player2 == 0) -> 1 // Jugador 1 gana
            else -> -1 // Jugador 2 gana
        }

        return result
    }

    //actualizar las puntuaciones de los jugadores después de cada partida
    private fun updateScore(result: Int) {
        val scoreText1 = findViewById<TextView>(R.id.scoreP1)
        val scoreText2 = findViewById<TextView>(R.id.scoreP2)
        val handler = Handler()

        when (result) {
            1 -> ScoreManager2.player1Score++
            -1 -> ScoreManager2.player2Score++
        }

        handler.postDelayed({

            // Actualiza las puntuaciones en las vistas
            scoreText1.text = ScoreManager2.player1Score.toString()
            scoreText2.text = ScoreManager2.player2Score.toString()

        }, 2000)

        showGif2()
    }

    //retrasa el tiempo para mostrar el resultado de la partida
    private fun showGif2() {
        val handler = Handler()

        handler.postDelayed({

            // Redirigir a la otra actividad
            val intent = Intent(this, ResultGif2::class.java)

            intent.putExtra("name1", name1)
            intent.putExtra("name2", name2)
            intent.putExtra("optionResult", optionResult)

            startActivity(intent)
            finish()
        }, 3000) // en milisegundos
    }

    private fun checkButtons() {

        // se inicializa en false , cuando ambos es true significa que han elegido sus opciones
        if (player1Clicked && player2Clicked) {

            val r1: Button = findViewById(R.id.rock1)
            val p1: Button = findViewById(R.id.paper1)
            val s1: Button = findViewById(R.id.scissors1)
            val r2: Button = findViewById(R.id.rock2)
            val p2: Button = findViewById(R.id.paper2)
            val s2: Button = findViewById(R.id.scissors2)

            // Deshabilita todos los botones después de que ambos jugadores han hecho clic
            r1.isEnabled = false
            p1.isEnabled = false
            s1.isEnabled = false
            r2.isEnabled = false
            p2.isEnabled = false
            s2.isEnabled = false

        }
    }
}

    // Clase para almacenar puntos
    object ScoreManager2 {
        var player1Score: Int = 0
        var player2Score: Int = 0
    }

