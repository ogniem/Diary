package com.diary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircularProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var progress: Int = 0
    private var maxProgress: Int = 100

    private val backgroundPaint = Paint().apply {
        color = Color.parseColor("#A3A3A3")
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 20f // Độ rộng của đường tròn
        strokeCap = Paint.Cap.ROUND // Đầu bo tròn
    }

    private val foregroundPaint = Paint().apply {
        color = Color.parseColor("#766DE6")
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 20f // Độ rộng của đường tròn
        strokeCap = Paint.Cap.ROUND // Đầu bo tròn
    }

    private val rectF = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width - paddingStart - paddingEnd) / 2f

        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        // Vẽ background đường tròn
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

        // Vẽ đường tròn biểu thị phần trăm
        val angle = 360f * progress / maxProgress
        canvas.drawArc(rectF, -90f, angle, false, foregroundPaint)
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }
}
