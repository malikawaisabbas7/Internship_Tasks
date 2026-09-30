package com.example.internshiptasks.Task5_PhoneCallsData

import android.Manifest
import com.example.internshiptasks.R
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.internshiptasks.Task5_PhoneCallsData.HostActivity

class PermissionActivity : AppCompatActivity() {
    private lateinit var tvPermissionTitle: TextView
    private lateinit var tvPermissionMessage: TextView
    private lateinit var checkBoxContacts: CheckBox
    private lateinit var tvContactsDescription: TextView
    private lateinit var checkboxRecentCalls: CheckBox
    private lateinit var tvRecentDescription: TextView
    private lateinit var btnContinue: Button

    companion object {
        private const val CONTACTS_PERMISSIONS_CODE = 100
        private const val RECENT_CALLS_PERMISSIONS_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_task5)

        tvPermissionTitle =
            findViewById(R.id.tvPermissionTitle)

        tvPermissionMessage =
            findViewById(R.id.tvPermissionMessage)

        checkBoxContacts =
            findViewById(R.id.checkboxContacts)

        tvContactsDescription =
            findViewById(R.id.tvContactsDescription)

        checkboxRecentCalls =
            findViewById(R.id.checkboxRecentCalls)

        tvRecentDescription =
            findViewById(R.id.tvRecentDescription)

        btnContinue =
            findViewById(R.id.btnContinue)

        btnContinue.visibility = Button.GONE


        checkBoxContacts.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestContactsPermissions()
            } else {
                updateContinueButton()
            }
        }


        checkboxRecentCalls.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestRecentCallsPermissions()
            } else {
                updateContinueButton()
            }
        }

        btnContinue.setOnClickListener {
            openMainActivity()
        }
        checkAlreadyGrantedPermissions()

    }
    private fun requestContactsPermissions() {
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            checkBoxContacts.isChecked = true

            updateContinueButton()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS
                ),
                CONTACTS_PERMISSIONS_CODE
            )
        }
    }

    private fun requestRecentCallsPermissions(){
        if(
            ContextCompat.checkSelfPermission(
                this,

                Manifest.permission.READ_CALL_LOG
            )==PackageManager.PERMISSION_GRANTED
        ){
            checkboxRecentCalls.isChecked = true
            updateContinueButton()
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_CALL_LOG,
                ),
                RECENT_CALLS_PERMISSIONS_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults)

        if (requestCode == CONTACTS_PERMISSIONS_CODE)
        {
            if (contactsPermissionGranted()) {
                checkBoxContacts.isChecked = true
            } else
            {
                checkBoxContacts.isChecked = false
                updateContinueButton()

            }
        }


        if (requestCode == RECENT_CALLS_PERMISSIONS_CODE) {
            if (recentCallsPermissionGranted()) {
                checkboxRecentCalls.isChecked = true
            }
            else
            {
                checkboxRecentCalls.isChecked = false
            }

            updateContinueButton()
        }
    }

    private fun contactsPermissionGranted(): Boolean {
        val readContactsGranted =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED

        val writeContactsGranted =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED

        return readContactsGranted && writeContactsGranted

    }

    private fun recentCallsPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CALL_LOG
        )== PackageManager.PERMISSION_GRANTED
    }


    private fun checkAlreadyGrantedPermissions(){
        if(contactsPermissionGranted()){
            checkBoxContacts.isChecked= true
        }
        if(recentCallsPermissionGranted()){
            checkboxRecentCalls.isChecked = true
         }
        updateContinueButton()
    }

    private fun updateContinueButton(){
        if(
            contactsPermissionGranted() &&
            recentCallsPermissionGranted()
        ){
            btnContinue.visibility = Button.VISIBLE
        }else{
            btnContinue.visibility = Button.GONE
        }
    }


    override fun onResume(){
        super.onResume()
        checkAlreadyGrantedPermissions()
    }

    private fun openMainActivity(){
        intent = Intent(
            this,
            HostActivity::class.java
        )
        startActivity(intent)

        finish()
    }

}