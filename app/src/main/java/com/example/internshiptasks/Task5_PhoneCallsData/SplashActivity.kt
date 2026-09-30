package com.example.internshiptasks.Task5_PhoneCallsData

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.internshiptasks.R
import androidx.appcompat.app.AppCompatActivity
import com.example.internshiptasks.Task5_PhoneCallsData.PermissionActivity

class SplashActivity : AppCompatActivity() {

    private val splashTime = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_task5)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(
                this,
                PermissionActivity::class.java
            )

            startActivity(intent)

            finish()

        }, splashTime)
    }
}