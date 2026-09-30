package com.example.internshiptasks.Task8_RoomDBConnection

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.internshiptasks.R

class UserDetailsScreen_RoomDB : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvName: TextView
    private lateinit var tvNumber: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvAge: TextView

    private lateinit var etNameEdit: EditText
    private lateinit var etNumberEdit: EditText
    private lateinit var etEmailEdit: EditText
    private lateinit var etAgeEdit: EditText

    private lateinit var spinnerGender: Spinner
    private lateinit var btnUpdate: Button

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_details_screen_room_db)

        tvTitle = findViewById(R.id.tvTitle)
        tvName = findViewById(R.id.tvName)
        tvNumber = findViewById(R.id.tvNumber)
        tvEmail = findViewById(R.id.tvEmail)
        tvAge = findViewById(R.id.tvAge)

        etNameEdit = findViewById(R.id.etNameEdit)
        etNumberEdit = findViewById(R.id.etNumberEdit)
        etEmailEdit = findViewById(R.id.etEmailEdit)
        etAgeEdit = findViewById(R.id.etAgeEdit)

        spinnerGender = findViewById(R.id.spinnerGender)
        btnUpdate = findViewById(R.id.btnUpdate)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user_database"
        ).build()

        userDao = database.userDao()

        setupGenderSpinner()

        userId = intent.getIntExtra("userId", -1)

        if (userId != -1) {

            getUserData(userId)

        } else {

            Toast.makeText(
                this,
                "User ID not found",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnUpdate.setOnClickListener {

            if (userId != -1) {
                updateUser(userId)
            }
        }
    }

    private fun getUserData(userId: Int) {

        Thread {

            val user = userDao.getUserById(userId)

            runOnUiThread {

                if (user != null) {

                    etNameEdit.setText(user.name)
                    etNumberEdit.setText(user.number)
                    etEmailEdit.setText(user.email)
                    etAgeEdit.setText(user.age)

                    val genderPosition = when (user.gender) {
                        "Male" -> 1
                        "Female" -> 2
                        else -> 0
                    }

                    spinnerGender.setSelection(genderPosition)

                } else {

                    Toast.makeText(
                        this,
                        "User not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    private fun updateUser(userId: Int) {

        val name = etNameEdit.text.toString().trim()
        val number = etNumberEdit.text.toString().trim()
        val email = etEmailEdit.text.toString().trim()
        val age = etAgeEdit.text.toString().trim()
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

        val updatedUser = User(
            id = userId,
            name = name,
            number = number,
            email = email,
            age = age,
            gender = gender
        )

        Thread {

            userDao.updateUser(updatedUser)

            runOnUiThread {

                Toast.makeText(
                    this,
                    "Data updated successfully",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }.start()
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