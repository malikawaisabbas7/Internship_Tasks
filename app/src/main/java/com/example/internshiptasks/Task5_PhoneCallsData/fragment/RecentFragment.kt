package com.example.internshiptasks.Task5_PhoneCallsData

import android.net.Uri
import com.example.internshiptasks.R
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecentFragment : Fragment() {

    private lateinit var recentAdapter: RecentAdapter
    private lateinit var recyclerViewRecent: RecyclerView

    companion object {
        private const val READ_CALL_LOG_REQUEST_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_recent_task5,
            container,
            false
        )

    }



    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewRecent =
            view.findViewById(R.id.recyclerViewRecent)

        setupRecyclerView()

        loadRecentCalls()
    }

    private fun setupRecyclerView() {

        recentAdapter = RecentAdapter(emptyList())

        recyclerViewRecent.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerViewRecent.adapter = recentAdapter
    }


    private fun loadRecentCalls() {

        val recentCalls = mutableListOf<RecentModel>()

        val cursor = requireContext().contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            arrayOf(
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION
            ),
            null,
            null,
            CallLog.Calls.DATE + " DESC"
        )

        cursor?.use {

            val numberIndex = it.getColumnIndex(
                CallLog.Calls.NUMBER
            )

            val typeIndex = it.getColumnIndex(
                CallLog.Calls.TYPE
            )

            val dateIndex = it.getColumnIndex(
                CallLog.Calls.DATE
            )

            val durationIndex = it.getColumnIndex(
                CallLog.Calls.DURATION
            )

            while (it.moveToNext()) {

                val phoneNumber =
                    it.getString(numberIndex)

                val callTypeValue =
                    it.getInt(typeIndex)

                val callDateValue =
                    it.getLong(dateIndex)

                val durationValue =
                    it.getLong(durationIndex)

                val callType =
                    getCallType(callTypeValue)

                val callDate =
                    formatDate(callDateValue)

                val duration =
                    formatDuration(durationValue)

                val contactName =
                    getContactName(phoneNumber)

                recentCalls.add(
                    RecentModel(
                        name = contactName,
                        phoneNumber = phoneNumber ?: "Unknown",
                        callType = callType,
                        date = callDate,
                        duration = duration
                    )
                )
            }
        }

        recentAdapter.updateList(recentCalls)
    }

    private fun getContactName(phoneNumber: String?): String {

        if (phoneNumber.isNullOrEmpty()) {
            return "Unknown"
        }

        val lookupUri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )

        val cursor = requireContext().contentResolver.query(
            lookupUri,
            arrayOf(
                ContactsContract.PhoneLookup.DISPLAY_NAME
            ),
            null,
            null,
            null
        )

        cursor?.use {

            if (it.moveToFirst()) {

                val nameIndex = it.getColumnIndex(
                    ContactsContract.PhoneLookup.DISPLAY_NAME
                )

                if (nameIndex >= 0) {
                    return it.getString(nameIndex)
                }
            }
        }

        return "Unknown"
    }

    private fun getCallType(type: Int): String {

        return when (type) {

            CallLog.Calls.INCOMING_TYPE -> "Incoming"

            CallLog.Calls.OUTGOING_TYPE -> "Outgoing"

            CallLog.Calls.MISSED_TYPE -> "Missed"

            CallLog.Calls.REJECTED_TYPE -> "Rejected"

            else -> "Unknown"
        }
    }

    private fun formatDate(timestamp: Long): String {

        val dateFormat = SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        )

        return dateFormat.format(Date(timestamp))
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