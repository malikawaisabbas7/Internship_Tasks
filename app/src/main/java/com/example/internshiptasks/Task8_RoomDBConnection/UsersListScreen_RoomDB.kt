package com.example.internshiptasks.Task8_RoomDBConnection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.internshiptasks.R

class UsersListScreen_RoomDB : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    private val userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_users_list_screen_room_db)

        recyclerView = findViewById(R.id.recyclerView)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user_database"
        ).build()

        userDao = database.userDao()

        recyclerView.layoutManager = LinearLayoutManager(this)

        userAdapter = UserAdapter(
            userList
        ) { userId ->

            val intent = Intent(
                this,
                UserDetailsScreen_RoomDB::class.java
            )

            intent.putExtra("userId", userId)

            startActivity(intent)
        }

        recyclerView.adapter = userAdapter
    }

    private fun getUsers() {

        Thread {

            val users = userDao.getAllUsers()

            runOnUiThread {

                userList.clear()
                userList.addAll(users)

                userAdapter.notifyDataSetChanged()
            }

        }.start()
    }

    override fun onResume() {
        super.onResume()

        getUsers()
    }
}