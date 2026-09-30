package com.example.internshiptasks.Task8_RoomDBConnection

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val number: String,
    val email: String,
    val age: String,
    val gender: String
)