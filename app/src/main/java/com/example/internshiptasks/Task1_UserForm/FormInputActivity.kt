
package com.example.internshiptasks.Task1_UserForm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.internshiptasks.R

class FormInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_form_task1)

        val etName = findViewById<EditText>(R.id.etName)
        val etNumber = findViewById<EditText>(R.id.etNumber)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etAge = findViewById<EditText>(R.id.etAge)
        val spinnerGender = findViewById<Spinner>(R.id.spinnerGender)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        val genders = arrayOf("Select gender", "Male", "Female")
        val adapter = android.widget.ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            genders
        )
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerGender.adapter = adapter

        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val number = etNumber.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val age = etAge.text.toString().trim()
            val gender = spinnerGender.selectedItem.toString()

            if (name.isEmpty() || number.isEmpty() ||
                email.isEmpty() || age.isEmpty() ||
                gender == "Select gender"
            ) {
                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Form submitted successfully!",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = android.content.Intent(
                    this,
                    FormOutputActivity::class.java
                )
                intent.putExtra("EXTRA_NAME", name)
                intent.putExtra("EXTRA_NUMBER", number)
                intent.putExtra("EXTRA_EMAIL", email)
                intent.putExtra("EXTRA_AGE", age)
                intent.putExtra("EXTRA_GENDER", gender)

                startActivity(intent)
            }
        }
    }
}
