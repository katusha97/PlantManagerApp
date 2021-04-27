package com.example.PlantManagerApp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class CustomView(context: Context?) : View(context) {
    private val rectangle: Rect
    private val paint: Paint
    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.BLUE)
        canvas.drawRect(rectangle, paint)
    }

    init {
        val x = 50
        val y = 50
        val sideLength = 200

        rectangle = Rect(x, y, sideLength, sideLength)

        paint = Paint()
        paint.color = Color.GRAY
    }
}