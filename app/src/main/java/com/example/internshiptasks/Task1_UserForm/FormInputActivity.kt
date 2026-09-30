package com.example.internshiptasks.Task1_UserForm

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.internshiptasks.R
import kotlin.apply
import kotlin.jvm.java
import kotlin.text.all
import kotlin.text.endsWith
import kotlin.text.isDigit
import kotlin.text.isEmpty
import kotlin.text.isLetter
import kotlin.text.isWhitespace
import kotlin.text.toIntOrNull
import kotlin.text.trim

class FormInputActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var btnSubmit: Button
    private lateinit var spinnerGender: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_form_task1)

        etName = findViewById(R.id.etName)
        etNumber = findViewById(R.id.etNumber)
        etEmail = findViewById(R.id.etEmail)
        etAge = findViewById(R.id.etAge)
        btnSubmit = findViewById(R.id.btnSubmit)
        spinnerGender = findViewById(R.id.spinnerGender)

        val genders = arrayOf("Select gender", "Male", "Female")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etName.error = null
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etEmail.error = null
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        btnSubmit.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val name = etName.text.toString().trim()
        val phone = etNumber.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val ageString = etAge.text.toString().trim()
        val gender = spinnerGender.selectedItem.toString()

        var isValid = true

        if (name.isEmpty() || !name.all { it.isLetter() || it.isWhitespace() }) {
            etName.error = "Name must contain only alphabets"
            isValid = false
        }

        if (phone.length != 11 || !phone.all { it.isDigit() }) {
            etNumber.error = "Phone Number must be exactly 11 digits..."
            isValid = false
        }

        val emailPattern = Patterns.EMAIL_ADDRESS
        if (email.isEmpty() || !emailPattern.matcher(email).matches() || !email.endsWith("@gmail.com")) {
            etEmail.error = "Enter a valid  email..."
            isValid = false
        }

        val ageInt = ageString.toIntOrNull()
        if (ageInt == null || ageInt < 0 || ageInt > 100) {
            etAge.error = "Age must be between 0 and 100."
            isValid = false
        }

        if (gender == "Select gender") {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (isValid) {
            Toast.makeText(this, "Form Submitted Successfully!", Toast.LENGTH_SHORT).show()

            val intent = Intent(
                this,
                FormOutputActivity::class.java).apply {
                putExtra("EXTRA_NAME", name)
                putExtra("EXTRA_NUMBER", phone)
                putExtra("EXTRA_EMAIL", email)
                putExtra("EXTRA_AGE", ageString)
                putExtra("EXTRA_GENDER", gender)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please enter correct values.", Toast.LENGTH_SHORT).show()
        }
    }
}