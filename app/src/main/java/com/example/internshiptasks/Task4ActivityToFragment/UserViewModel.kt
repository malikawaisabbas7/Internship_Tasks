package com.example.activitytofragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class UserData(
    val name: String,
    val phone: String,
    val email: String,
    val age: String,
    val gender: String
)

class UserViewModel : ViewModel() {
    private val _userResult = MutableLiveData<UserData>()

    val userResult: LiveData<UserData> = _userResult

    fun sendData(data: UserData) {
        _userResult.value = data
    }
}