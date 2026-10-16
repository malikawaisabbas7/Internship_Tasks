package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.activity

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.internshiptasks.R
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.fragment.ContactsFragmentRepo_and_MVVM
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.fragment.FavouriteFragmentRepo_and_MVVM
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.fragment.RecentFragmentRepo_and_MVVM
import com.google.android.material.bottomnavigation.BottomNavigationView

class HostActivityRepo_and_MVVM : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_repo_and_mvvm_task17)

        bottomNavigation = findViewById(R.id.bottomNavigation)
        fragmentContainer = findViewById(R.id.fragmentContainer)

        if (savedInstanceState == null) {
            loadFragment(RecentFragmentRepo_and_MVVM())
        }

        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {

        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_recent -> {
                    loadFragment(RecentFragmentRepo_and_MVVM())
                    true
                }

                R.id.nav_contacts -> {
                    loadFragment(ContactsFragmentRepo_and_MVVM())
                    true
                }

                R.id.nav_favourite -> {
                    loadFragment(FavouriteFragmentRepo_and_MVVM())
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                fragment
            )
            .commit()

    }

}