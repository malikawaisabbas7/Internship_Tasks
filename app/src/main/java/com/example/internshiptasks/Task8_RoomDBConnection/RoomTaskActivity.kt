package com.example.internshiptasks.Task8_RoomDBConnection

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.internshiptasks.R

class RoomTaskActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var btnSubmit: Button
    private lateinit var btnSeeUsers: Button

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_room_task_task8)

        etName = findViewById(R.id.etName)
        etNumber = findViewById(R.id.etNumber)
        etEmail = findViewById(R.id.etEmail)
        etAge = findViewById(R.id.etAge)
        spinnerGender = findViewById(R.id.spinnerGender)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnSeeUsers = findViewById(R.id.btnSeeUsers)

        setupGenderSpinner()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user_database"
        ).build()

        userDao = database.userDao()

        btnSubmit.setOnClickListener {
            saveUser()
        }

        btnSeeUsers.setOnClickListener {
            val intent = Intent(
                this,
                UsersListScreen_RoomDB::class.java
            )

            startActivity(intent)
        }
    }

    private fun saveUser() {

        val name = etName.text.toString().trim()
        val number = etNumber.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val age = etAge.text.toString().trim()
        val gender = spinnerGender.selectedItem.toString()

        if (
            name.isEmpty() ||
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

        val user = User(
            name = name,
            number = number,
            email = email,
            age = age,
            gender = gender
        )

        Thread {

            try {

                userDao.insertUser(user)

                runOnUiThread {

                    Toast.makeText(
                        this,
                        "Data saved successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    clearForm()
                }

            } catch (e: Exception) {

                runOnUiThread {

                    Toast.makeText(
                        this,
                        "Error saving data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }.start()
    }

    private fun clearForm() {

        etName.text.clear()
        etNumber.text.clear()
        etEmail.text.clear()
        etAge.text.clear()

        spinnerGender.setSelection(0)
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