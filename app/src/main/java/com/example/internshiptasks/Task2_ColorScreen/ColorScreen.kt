package com.example.internshiptasks.Task2_ColorScreen

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.internshiptasks.R

class ColorScreen : AppCompatActivity() {

    private var colorToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.color_screen_task2)

        val redView = findViewById<View>(R.id.viewRed)
        val yellowView = findViewById<View>(R.id.viewYellow)
        val greenView = findViewById<View>(R.id.viewGreen)

        redView.setOnClickListener {
            showColorToast("Red", Color.RED)
        }

        yellowView.setOnClickListener {
            showColorToast("Yellow", Color.YELLOW)
        }

        greenView.setOnClickListener {
            showColorToast("Green", Color.GREEN)
        }
    }

    private fun showColorToast(colorName: String, borderColor: Int) {

        val textView = TextView(this).apply {
            text = colorName
            textSize = 40f
            setTextColor(Color.WHITE)
            setPadding(80, 40, 80, 40)
            gravity = Gravity.CENTER

            val backgroundDrawable = GradientDrawable().apply {
                setColor(Color.parseColor("#AA000000"))
                cornerRadius = 100f
                setStroke(8, borderColor)
            }

            background = backgroundDrawable
        }

        colorToast?.cancel()

        colorToast = Toast(applicationContext).apply {
            duration = Toast.LENGTH_SHORT
            view = textView
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    override fun onStop() {

        colorToast?.cancel()
        colorToast = null

        super.onStop()
    }
}
