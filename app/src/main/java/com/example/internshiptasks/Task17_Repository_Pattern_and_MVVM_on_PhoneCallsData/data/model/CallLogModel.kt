package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.model

data class CallLogModel(
    val contactName: String,
    val phoneNumber: String,
    val callType: String,
    val callDate: String,
    val callDuration: String
)