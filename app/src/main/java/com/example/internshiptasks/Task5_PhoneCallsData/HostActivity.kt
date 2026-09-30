package com.example.internshiptasks.Task5_PhoneCallsData

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.internshiptasks.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class HostActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_host_task5)

        bottomNavigation = findViewById(R.id.bottomNavigation)
        fragmentContainer = findViewById(R.id.fragmentContainer)

        if (savedInstanceState == null) {
            loadFragment(RecentFragment())
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {

        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_recent -> {
                    loadFragment(RecentFragment())
                    true
                }

                R.id.nav_contacts -> {
                    loadFragment(ContactsFragment())
                    true
                }

                R.id.nav_favourite -> {
                    loadFragment(FavouriteFragment())
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