package com.example.internshiptasks.Task5_PhoneCallsData

import kotlin.time.Duration


data class RecentModel(
    val name : String,
    val phoneNumber: String,
    val callType: String,
    val date: String,
    val duration: String
)