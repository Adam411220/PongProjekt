package com.example.pong

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.channels.Channel
import java.util.*

class GameView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs), SurfaceHolder.Callback {
    lateinit var ball: Ball
    lateinit var paddle: Paddle
    var screenWidth = 0
    var screenHeight = 0
    var controls : MainActivity.Controls = MainActivity.Controls.NONE
    private val thread: GameThread = GameThread(holder, this)
    val scoreChannel = Channel<Int>()
    var score = 0
    init {
        holder.addCallback(this)
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        ball = Ball(holder.surfaceFrame.width(), holder.surfaceFrame.height())
        screenWidth = holder.surfaceFrame.width()
        screenHeight = holder.surfaceFrame.height()
        paddle = Paddle(screenWidth, screenHeight)
        thread.running = true
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.running = false
        thread.join()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (ball != null) {

        }
        val red = Paint().apply {
            color = Color.RED
        }
        canvas?.drawOval(ball.xPos, ball.yPos, ball.xPos + ball.ballWidth, ball.yPos + ball.ballHeight, red)

        val green = Paint().apply {
            color = Color.GREEN
        }

        canvas?.drawRect(paddle.xPos, paddle.yPos - paddle.height, paddle.xPos + paddle.width, paddle.yPos, green)

    }

    fun update() {
        ball.xPos += ball.xVel
        ball.yPos += ball.yVel
        ball.updateRect()

        when(controls) {
            MainActivity.Controls.RIGHT -> paddle.xPos += paddle.paddleSpeed
            MainActivity.Controls.LEFT -> paddle.xPos -= paddle.paddleSpeed
        }

        paddle.updateRect()


        // side wall hit
        if (ball.xPos <= 0 || ball.xPos + ball.ballWidth >= screenWidth) {
            ball.reverseX()
        }

        // upper wall hit

        if (ball.yPos <= 0) {
            ball.reverseY()
        }

        //paddle hit
        if (RectF.intersects(ball.rect, paddle.rect)) {
            ball.reverseY()
            score += 1
            if (ball.xVel < 0) {
                ball.xVel -= 5
            } else {
                ball.xVel += 5
            }
            if (ball.yVel < 0) {
                ball.yVel -= 5
            } else {
                ball.yVel += 5
            }
        }
    }


}