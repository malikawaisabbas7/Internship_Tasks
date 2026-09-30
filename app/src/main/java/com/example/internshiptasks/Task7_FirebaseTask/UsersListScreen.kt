package com.example.internshiptasks.Task7_FirebaseTask

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.example.internshiptasks.R
import com.example.internshiptasks.Task7_FirebaseTask.UserAdapter
class UsersListScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var firestore: FirebaseFirestore
    private val userList = ArrayList<UserModel>()
    private val userIdList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_second_task7)

        recyclerView = findViewById(R.id.recyclerView)

        firestore = FirebaseFirestore.getInstance()

        recyclerView.layoutManager = LinearLayoutManager(this)


        userAdapter = UserAdapter(
            userList,
            userIdList
        ) { userId ->

            val intent = Intent(
                this,
                UserDetailsScreen::class.java
            )

            intent.putExtra("userId", userId)

            startActivity(intent)
        }

        recyclerView.adapter = userAdapter

        getUsers()

    }

    private fun getUsers() {

        firestore.collection("Users")
            .get()
            .addOnSuccessListener { result ->

                userList.clear()
                userIdList.clear()

                for (document in result) {

                    val user = document.toObject(
                        UserModel::class.java
                    )

                    userList.add(user)
                    userIdList.add(document.id)
                }

                userAdapter.notifyDataSetChanged()

            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Failed to fetch users",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onResume() {
        super.onResume()
        getUsers()
    }
}