package com.example.internshiptasks.Task4ActivityToFragment

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.internshiptasks.R
class FormActivity : AppCompatActivity(),
    UserInfoFragment.OnUserDataSubmitListener {

    private lateinit var tvName: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvGender: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_form_task4)

        tvName = findViewById(R.id.tvName)
        tvPhone = findViewById(R.id.tvPhone)
        tvEmail = findViewById(R.id.tvEmail)
        tvAge = findViewById(R.id.tvAge)
        tvGender = findViewById(R.id.tvGender)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    UserInfoFragment()
                )
                .commit()
        }
    }

    override fun onUserDataSubmit(
        name: String,
        phone: String,
        email: String,
        age: String,
        gender: String
    ) {
        tvName.text = "Name: $name"
        tvPhone.text = "Phone: $phone"
        tvEmail.text = "Email: $email"
        tvAge.text = "Age: $age"
        tvGender.text = "Gender: $gender"
    }
}