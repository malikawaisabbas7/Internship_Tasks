package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.internshiptasks.R
import androidx.appcompat.app.AppCompatActivity

class SplashActivityRepo_and_MVVM : AppCompatActivity() {

    private val splashTime = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_mvvm_and_repository_task17)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(
                this,
                PermissionActivityRepo_and_MVVM::class.java
            )

            startActivity(intent)

            finish()

        }, splashTime)
    }
}