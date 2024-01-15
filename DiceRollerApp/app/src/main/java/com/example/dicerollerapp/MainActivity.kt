package com.example.dicerollerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import com.example.dicerollerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val imageArray = arrayOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            showAnimation()
        }


    }

    private fun rollDice():Int {
        val dice = Dice(6)
        return dice.roll()
    }


    /**
     * Dice with a fixed number of sides.
     */
    class Dice(private val numSides: Int) {

        /**
         * Do a random dice roll and return the result.
         */
        fun roll(): Int {
            return (1..numSides).random()
        }
    }

    private fun showAnimation(){

        val fadeInAnimation = AlphaAnimation(0.0f, 1.0f)
        fadeInAnimation.duration = 200 // Duración en milisegundos
        val result = rollDice()

        // Establece un escuchador de animación
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // La animación ha comenzado
            }

            override fun onAnimationEnd(animation: Animation?) {

                val randomImage = imageArray[(Math.random() * imageArray.size).toInt()]
                binding.dice.setImageResource(randomImage)
                binding.dice.startAnimation(fadeInAnimation)

                Handler().postDelayed({
                    binding.dice.clearAnimation()
                    binding.dice.setImageResource(imageArray[result]-1)
                    binding.result.text  = result.toString()
                }, 1000)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // La animación se repite
            }
        })

        // Inicia la animación
        binding.dice.startAnimation(fadeInAnimation)
    }
    }
