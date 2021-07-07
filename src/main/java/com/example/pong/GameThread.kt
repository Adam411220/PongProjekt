package com.example.pong

import android.graphics.Canvas
import android.view.SurfaceHolder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameThread(private val surfaceHolder: SurfaceHolder, private val gameView: GameView) : Thread() {
    var running = true
    private var canvas : Canvas? = null
    private val targetFPS = 30

    override fun run() {
        var startTime: Long
        var timeMilis : Long
        var waitTime : Long
        var targetTime = (1000/targetFPS).toLong()

        while (running) {
            startTime = System.nanoTime()
            var canvas = surfaceHolder.lockCanvas()
            gameView.draw(canvas)
            gameView.update()
            surfaceHolder.unlockCanvasAndPost(canvas)
            if (gameView.ball.yPos + gameView.ball.ballHeight >= gameView.screenHeight) {
                GlobalScope.launch {
                    gameView.scoreChannel.send(gameView.score)
                }
                running = false

            }
            timeMilis = (System.nanoTime() - startTime) * 1000000
            waitTime = targetTime - timeMilis
            if (waitTime >= 0) {
                sleep(waitTime)
            }
        }
    }
}