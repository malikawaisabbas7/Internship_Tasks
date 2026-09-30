package com.example.internshiptasks.Task7_FirebaseTask

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.example.internshiptasks.Task7_FirebaseTask.UserModel
import com.example.internshiptasks.R
class UserDetailsScreen : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvNumber: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvGender: TextView

    private lateinit var etNameEdit: EditText
    private lateinit var etNumberEdit: EditText
    private lateinit var etEmailEdit: EditText
    private lateinit var etAgeEdit: EditText

    private lateinit var spinnerGender: Spinner
    private lateinit var btnUpdate: Button

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_third_task7)

        tvName = findViewById(R.id.tvName)
        tvNumber = findViewById(R.id.tvNumber)
        tvEmail = findViewById(R.id.tvEmail)
        tvAge = findViewById(R.id.tvAge)
        tvGender = findViewById(R.id.tvGender)

        etNameEdit = findViewById(R.id.etNameEdit)
        etNumberEdit = findViewById(R.id.etNumberEdit)
        etEmailEdit = findViewById(R.id.etEmailEdit)
        etAgeEdit = findViewById(R.id.etAgeEdit)

        spinnerGender = findViewById(R.id.spinnerGender)
        btnUpdate = findViewById(R.id.btnUpdate)

        firestore = FirebaseFirestore.getInstance()

        setupGenderSpinner()

        val userId = intent.getStringExtra("userId")

        if (userId != null) {

            getUserData(userId)

            btnUpdate.setOnClickListener {
                updateUser(userId)
            }

        } else {

            Toast.makeText(
                this,
                "User ID not found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getUserData(userId: String) {

        firestore.collection("Users")
            .document(userId)
            .get()
            .addOnSuccessListener { data ->

                if (data.exists()) {

                    val user = data.toObject(
                        UserModel::class.java
                    )

                    if (user != null) {

                        tvName.text = "Name"
                        tvNumber.text = "Number"
                        tvEmail.text = "Email"
                        tvAge.text = "Age"
                        tvGender.text = "Gender"

                        etNameEdit.setText(user.name)
                        etNumberEdit.setText(user.number)
                        etEmailEdit.setText(user.email)
                        etAgeEdit.setText(user.age)

                        val genderPosition =
                            when (user.gender) {
                                "Male" -> 1
                                "Female" -> 2
                                else -> 0
                            }

                        spinnerGender.setSelection(genderPosition)
                    }

                } else {

                    Toast.makeText(
                        this,
                        "User not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Failed to fetch user",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun updateUser(userId: String) {

        val name = etNameEdit.text.toString()
        val number = etNumberEdit.text.toString()
        val email = etEmailEdit.text.toString()
        val age = etAgeEdit.text.toString()
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

            return
        }

        val user = UserModel(
            name,
            number,
            email,
            age,
            gender
        )

        firestore.collection("Users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Data updated successfully",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Failed to update data",
                    Toast.LENGTH_SHORT
                ).show()
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