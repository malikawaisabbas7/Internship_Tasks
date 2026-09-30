package com.example.internshiptasks.Task1_UserForm

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.internshiptasks.R

class FormOutputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_task1)

        val name = intent.getStringExtra("EXTRA_NAME")
        val number = intent.getStringExtra("EXTRA_NUMBER")
        val email = intent.getStringExtra("EXTRA_EMAIL")
        val age = intent.getStringExtra("EXTRA_AGE")
        val gender = intent.getStringExtra("EXTRA_GENDER")

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvNumber = findViewById<TextView>(R.id.tvNumber)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvAge = findViewById<TextView>(R.id.tvAge)
        val tvGender = findViewById<TextView>(R.id.tvGender)

        tvName.text = name
        tvNumber.text = number
        tvEmail.text = email
        tvAge.text = age
        tvGender.text = gender

    }

}