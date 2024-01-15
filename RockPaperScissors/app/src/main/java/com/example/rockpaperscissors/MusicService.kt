package com.example.rockpaperscissors

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {

        private lateinit var mediaPlayer: MediaPlayer

        override fun onCreate() {
            super.onCreate()

            // Inicializa el reproductor de música con un archivo de audio en bruto (R.raw.music)
            mediaPlayer = MediaPlayer.create(this, R.raw.music)

            // Configura el reproductor para que la música se reproduzca en bucle
            mediaPlayer.isLooping = true
        }

        override fun onBind(intent: Intent?): IBinder? {
            return null
        }

        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            // Inicia la reproducción de la música
            mediaPlayer.start()
            return START_STICKY
        }

        override fun onDestroy() {
            // Detiene y libera el reproductor de música cuando el servicio se destruye
            mediaPlayer.stop()
            mediaPlayer.release()
            super.onDestroy()
        }
    }
