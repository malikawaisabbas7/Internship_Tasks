package com.example.internshiptasks

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.internshiptasks.Task1_UserForm.FormInputActivity
import com.example.internshiptasks.Task2_ColorScreen.ColorScreen
import com.example.internshiptasks.Task3_ResetButton.CounterActivity
import com.example.internshiptasks.Task4ActivityToFragment.FormActivity
import com.example.internshiptasks.Task5_PhoneCallsData.PermissionActivity
import com.example.internshiptasks.Task6_Fragment_To_Fragment.HostActivityforFragments
import com.example.internshiptasks.Task7_FirebaseTask.FormSubmitActivity
import com.example.internshiptasks.Task8_RoomDBConnection.RoomTaskActivity
import com.example.internshiptasks.Task9_Weather_and_News_app.LocationPermission
import com.example.internshiptasks.Task10_Firebase_Notification_App.FirebaseNotificationActivity
import com.example.internshiptasks.Task11_MVVM_on_WeatherApp.LocationPermissionActivity
import com.example.internshiptasks.Task12_MVC_on_WeatherApp.LocationPermissionActivity_MVC
import com.example.internshiptasks.Task13_MVP_on_WeatherApp.LocationPermissionActivityMVP
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.LocationPermissionActivityMVI
class MainActivity : AppCompatActivity() {

    private lateinit var btnUserFormTask: Button
    private lateinit var btnColorScreenTask: Button
    private lateinit var btnResetButtonTask: Button
    private lateinit var btnActivityToFragmentTask: Button
    private lateinit var btnPhoneCallsTask: Button
    private lateinit var btnFragmentToFragmentTask: Button
    private lateinit var btnRoomdbTask: Button
    private lateinit var btnFirebaseTask: Button
    private lateinit var btnWeatherTask: Button
    private lateinit var btnFirebaseNotificationTask: Button
    private lateinit var btnMvvmTask: Button
    private lateinit var btnMVCTask: Button
    private lateinit var btnMVPTask: Button
    private lateinit var btnMVITask: Button





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnUserFormTask = findViewById(R.id.btnUserFormTask)
        btnColorScreenTask = findViewById(R.id.btnColorScreenTask)
        btnResetButtonTask = findViewById(R.id.btnResetButtonTask)
        btnActivityToFragmentTask = findViewById(R.id.btnActivityToFragmentTask)
        btnPhoneCallsTask = findViewById(R.id.btnPhoneCallsTask)
        btnFragmentToFragmentTask = findViewById(R.id.btnFragmentToFragmentTask)
        btnFirebaseTask = findViewById(R.id.btnFirebaseTask)
        btnRoomdbTask = findViewById(R.id.btnRoomdbTask)
        btnWeatherTask = findViewById(R.id.btnWeatherTask)
        btnFirebaseNotificationTask = findViewById(R.id.btnFirebaseNotificationTask)
        btnMvvmTask = findViewById(R.id.btnMvvmTask)
        btnMVCTask = findViewById(R.id.btnMVCTask)
        btnMVPTask = findViewById(R.id.btnMVPTask)
        btnMVITask = findViewById(R.id.btnMVITask)

        btnUserFormTask.setOnClickListener {
            startActivity(Intent(this, FormInputActivity::class.java))
        }
        btnColorScreenTask.setOnClickListener {
            startActivity(Intent(this, ColorScreen::class.java))
        }

        btnResetButtonTask.setOnClickListener {
            startActivity(Intent(this, CounterActivity::class.java))
        }

        btnActivityToFragmentTask.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }

        btnPhoneCallsTask.setOnClickListener {
            startActivity(Intent(this, PermissionActivity::class.java))
        }

        btnFragmentToFragmentTask.setOnClickListener {
            startActivity(Intent(this, HostActivityforFragments::class.java))
        }

        btnFirebaseTask.setOnClickListener {
            startActivity(Intent(this, FormSubmitActivity::class.java))
        }

        btnRoomdbTask.setOnClickListener {
            startActivity(Intent(this, RoomTaskActivity::class.java))
        }

        btnWeatherTask.setOnClickListener {
            startActivity(Intent(this, LocationPermission::class.java))
        }

        btnFirebaseNotificationTask.setOnClickListener {
            startActivity(Intent(this, FirebaseNotificationActivity::class.java))
        }

        btnMvvmTask.setOnClickListener {
            startActivity(Intent(this, LocationPermissionActivity::class.java))
        }

        btnMVCTask.setOnClickListener{
            startActivity(Intent(this, LocationPermissionActivity_MVC::class.java))
        }

        btnMVPTask.setOnClickListener{
            startActivity(Intent(this, LocationPermissionActivityMVP::class.java))
        }

        btnMVITask.setOnClickListener{
            startActivity(Intent(this, LocationPermissionActivityMVI::class.java))
        }

    }


}