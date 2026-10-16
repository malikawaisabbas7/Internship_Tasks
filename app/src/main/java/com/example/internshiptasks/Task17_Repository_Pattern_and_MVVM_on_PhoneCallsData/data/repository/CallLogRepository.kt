package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.CallLog
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.model.CallLogModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CallLogRepository(private val context: Context) {

    fun getRecentCalls(): List<CallLogModel> {
        val result = mutableListOf<CallLogModel>()

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw SecurityException("Call log permission is not granted")
        }

        val projection = arrayOf(
            CallLog.Calls.NUMBER,
            CallLog.Calls.TYPE,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION
        )

        context.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            projection,
            null,
            null,
            "${CallLog.Calls.DATE} DESC"
        )?.use { cursor ->

            val numberIndex =
                cursor.getColumnIndex(CallLog.Calls.NUMBER)
            val typeIndex =
                cursor.getColumnIndex(CallLog.Calls.TYPE)
            val dateIndex =
                cursor.getColumnIndex(CallLog.Calls.DATE)
            val durationIndex =
                cursor.getColumnIndex(CallLog.Calls.DURATION)

            while (cursor.moveToNext()) {
                val number = cursor.getString(numberIndex).orEmpty()
                val type = cursor.getInt(typeIndex)
                val date = cursor.getLong(dateIndex)
                val duration = cursor.getLong(durationIndex)

                result.add(
                    CallLogModel(
                        contactName = getContactName(number),
                        phoneNumber = number,
                        callType = getCallType(type),
                        callDate = formatDate(date),
                        callDuration = formatDuration(duration)
                    )
                )
            }
        }

        return result
    }

    private fun getContactName(number: String): String {
        if (number.isBlank()) return "Unknown"

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return number
        }

        val uri = ContactsContract.PhoneLookup.CONTENT_FILTER_URI
            .buildUpon()
            .appendPath(number)
            .build()

        return context.contentResolver.query(
            uri,
            arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME),
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                cursor.getString(0) ?: number
            } else {
                number
            }
        } ?: number
    }

    private fun getCallType(type: Int): String {
        return when (type) {
            CallLog.Calls.INCOMING_TYPE -> "Incoming"
            CallLog.Calls.OUTGOING_TYPE -> "Outgoing"
            CallLog.Calls.MISSED_TYPE -> "Missed"
            CallLog.Calls.REJECTED_TYPE -> "Rejected"
            else -> "Other"
        }
    }

    private fun formatDate(timestamp: Long): String {
        return SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        ).format(Date(timestamp))
    }

    private fun formatDuration(seconds: Long): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60

        return String.format(
            Locale.getDefault(),
            "%02d:%02d",
            minutes,
            remainingSeconds
        )
    }
}
