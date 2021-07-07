package com.example.pong

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    enum class Controls {
        LEFT, RIGHT, NONE
    }
    lateinit var gameView: GameView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameView = findViewById(R.id.gameView)
        val buttonLeft : Button = findViewById(R.id.buttonLeft)
        buttonLeft.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> gameView.controls = Controls.LEFT
                MotionEvent.ACTION_UP -> gameView.controls = Controls.NONE
            }
            return@setOnTouchListener view.onTouchEvent(event)
        }
        val buttonRight: Button = findViewById(R.id.buttonRight)
        buttonRight.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> gameView.controls = Controls.RIGHT
                MotionEvent.ACTION_UP -> gameView.controls = Controls.NONE
            }
            return@setOnTouchListener view.onTouchEvent(event)
        }

        GlobalScope.launch {
            var score = gameView.scoreChannel.receive()
            returnScore(score)
        }


    }

    private fun returnScore(score: Int) {
        val myIntent = Intent()
        myIntent.putExtra("SCORE", score)
        setResult(RESULT_OK, myIntent)
        finish()
    }


}
