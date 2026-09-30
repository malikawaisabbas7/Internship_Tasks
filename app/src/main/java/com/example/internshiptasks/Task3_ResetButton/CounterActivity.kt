
package com.example.internshiptasks.Task3_ResetButton

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.internshiptasks.R

class CounterActivity : AppCompatActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter_task3)

        val tvCounter = findViewById<TextView>(R.id.tvCounter)
        val btnIncrease = findViewById<Button>(R.id.btnIncrease)
        val btnDecrease = findViewById<Button>(R.id.btnDecrease)
        val btnReset = findViewById<Button>(R.id.btnReset)

        btnIncrease.setOnClickListener {
            when {
                count < 10 -> {
                    count++
                    tvCounter.text = count.toString()

                    if (count == 0) Toast.makeText(this, "Value becomes 0.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnDecrease.setOnClickListener {
            when {
                count > -10 -> {
                    count--
                    tvCounter.text = count.toString()

                    if (count == 0) Toast.makeText(this, "Value becomes 0.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnReset.setOnClickListener {
            when (count) {
                0 -> {
                    Toast.makeText(this, "Already 0.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    count = 0
                    tvCounter.text = count.toString()
                    Toast.makeText(this, "Value becomes 0.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}