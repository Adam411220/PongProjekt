package com.example.pong

import android.graphics.RectF

class Ball(screenX: Int, screenY: Int) {
    var ballWidth = screenY / 20f
    var ballHeight = ballWidth


    var xVel = screenX / 110f
    var yVel = xVel

    var xPos = 0f
    var yPos = 0f

    var rect: RectF = RectF(xPos, yPos, xPos + ballWidth, yPos + ballHeight)

    fun updateRect() {
        rect.left = xPos
        rect.top = yPos
        rect.right = xPos + ballWidth
        rect.bottom = yPos + ballHeight
    }

    fun reverseX() {
        xVel = -xVel
    }

    fun reverseY() {
        yVel = -yVel
    }

}