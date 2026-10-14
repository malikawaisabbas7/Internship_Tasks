package com.example.internshiptasks.Task15_Singleton_Pattern_on_PhoneCallsData

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.internshiptasks.R
import androidx.appcompat.app.AppCompatActivity

class SplashActivity_Singleton : AppCompatActivity() {

    private val splashTime = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_task5)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(
                this,
                PermissionActivity_Singleton::class.java
            )

            startActivity(intent)

            finish()

        }, splashTime)
    }
}