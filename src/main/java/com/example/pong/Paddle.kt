package com.example.pong

import android.graphics.RectF

class Paddle(screenX: Int, screenY: Int) {
    var width = screenX / 5f
    var height = screenY / 40f
    var xPos = screenX / 2f
    val yPos = (screenY-height*2).toFloat()
    var rect: RectF = RectF(xPos, yPos, xPos + width, yPos + height)
    var paddleSpeed = width / 10f

    fun updateRect() {
        rect.left = xPos
        rect.right = xPos + width
    }
}