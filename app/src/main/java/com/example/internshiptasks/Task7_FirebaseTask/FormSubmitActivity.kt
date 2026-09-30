package com.example.internshiptasks.Task7_FirebaseTask


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.example.internshiptasks.Task7_FirebaseTask.UserModel
import com.example.internshiptasks.Task7_FirebaseTask.UsersListScreen
import com.example.internshiptasks.R
import com.example.internshiptasks.Task7_FirebaseTask.FormSubmitActivity
class FormSubmitActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var btnSubmit: Button
    private lateinit var btnSeeUsers: Button

    private lateinit var firestore: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_form_submit_task7)

        etName = findViewById(R.id.etName)
        etNumber = findViewById(R.id.etNumber)
        etEmail = findViewById(R.id.etEmail)
        etAge = findViewById(R.id.etAge)
        spinnerGender = findViewById(R.id.spinnerGender)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnSeeUsers = findViewById(R.id.btnSeeUsers)

        firestore = FirebaseFirestore.getInstance()

        setupGenderSpinner()

        btnSubmit.setOnClickListener {

            val name = etName.text.toString()
            val number = etNumber.text.toString()
            val email = etEmail.text.toString()
            val age = etAge.text.toString()
            val gender = spinnerGender.selectedItem.toString()

            if (name.isEmpty() ||
                number.isEmpty() ||
                email.isEmpty() ||
                age.isEmpty() ||
                gender == "Select Gender"
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val user = UserModel(
                    name,
                    number,
                    email,
                    age,
                    gender
                )

                firestore.collection("Users")
                    .add(user)
                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Data uploaded successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        etName.text.clear()
                        etNumber.text.clear()
                        etEmail.text.clear()
                        etAge.text.clear()
                        spinnerGender.setSelection(0)
                    }
                    .addOnFailureListener {

                        Toast.makeText(
                            this,
                            "Failed to upload data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }

        btnSeeUsers.setOnClickListener {

            val intent = Intent(
                this,
                UsersListScreen::class.java
            )

            startActivity(intent)
        }
    }

    private fun setupGenderSpinner() {

        val genders = arrayOf(
            "Select Gender",
            "Male",
            "Female"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            genders
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerGender.adapter = adapter
    }
}