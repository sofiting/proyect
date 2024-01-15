package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Tingmei Huang 2DAM

        // Encuentra las vistas (botones y texto) en el diseño de actividad
        val btPvC : Button = findViewById(R.id.btPvC)
        val btPvP : Button = findViewById(R.id.btPvP)
        val instruction : TextView = findViewById(R.id.instruction)

        // Inicia un servicio de música en segundo plano
        val musicServiceIntent = Intent(this, MusicService::class.java)
        startService(musicServiceIntent)

        // Define acciones para los botones de "Player vs Pc" y "Player vs Player"
        btPvC.setOnClickListener {
            val playPC = Intent(this, PvC::class.java)
            startActivity(playPC)
        }

        btPvP.setOnClickListener {
            val playPP = Intent(this, PlayerVsPlayer::class.java)
            startActivity(playPP)
        }

        // Agrega un clic en el TextView para mostrar las instrucciones
        instruction.setOnClickListener {
            showInstructionsDialog()
        }
    }

    // Función para mostrar un cuadro de diálogo con instrucciones del juego
    private fun showInstructionsDialog() {
        // Crea un cuadro de diálogo de instrucciones
        val dialog = AlertDialog.Builder(this)

        dialog.setTitle("Rock Paper Scissors Rules:")

        dialog.setMessage("Rock:\n" +
                "Wins against scissors.\n" +
                "Loses to paper.\n" +
                "Ties with rock.\n" + "\n"+
                "Paper:\n" +
                "Wins against rock.\n" +
                "Loses to scissors.\n" +
                "Ties with paper.\n" + "\n"+
                "Scissors:\n" +
                "Wins against paper.\n" +
                "Loses to rock.\n" +
                "Ties with scissors.")

        // Botón para cerrar el cuadro de diálogo
        dialog.setPositiveButton("Close") { _, _ -> }

        dialog.show()
    }
}
