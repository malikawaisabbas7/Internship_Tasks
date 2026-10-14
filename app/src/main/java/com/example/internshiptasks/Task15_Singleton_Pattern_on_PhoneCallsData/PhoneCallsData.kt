package com.example.internshiptasks.Task15_Singleton_Pattern_on_PhoneCallsData


import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PhoneCallsData {

    fun getContacts(context: Context): List<ContactModel> {
        val contacts = mutableListOf<ContactModel>()
        val contactIds = mutableSetOf<String>()
        val resolver = context.contentResolver

        try {
            val cursor = resolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.STARRED
                ),
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )

            cursor?.use {
                val idIndex = it.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                )
                val nameIndex = it.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )
                val numberIndex = it.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                )
                val starredIndex = it.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.STARRED
                )

                if (idIndex >= 0 && nameIndex >= 0 &&
                    numberIndex >= 0 && starredIndex >= 0
                ) {
                    while (it.moveToNext()) {
                        val id = it.getString(idIndex)

                        if (contactIds.add(id)) {
                            contacts.add(
                                ContactModel(
                                    id = id,
                                    name = it.getString(nameIndex),
                                    phoneNumber = it.getString(numberIndex),
                                    isFavourite = it.getInt(starredIndex) == 1
                                )
                            )
                        }
                    }
                }
            }
        } catch (_: SecurityException) {
        }

        return contacts
    }

    fun getFavouriteContacts(context: Context): List<ContactModel> {
        val favourites = mutableListOf<ContactModel>()
        val contactIds = mutableSetOf<String>()
        val resolver = context.contentResolver

        try {
            val cursor = resolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.STARRED
                ),
                ContactsContract.CommonDataKinds.Phone.STARRED + " = ?",
                arrayOf("1"),
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )

            cursor?.use {
                val idIndex = it.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                )
                val nameIndex = it.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )
                val numberIndex = it.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                )
                val starredIndex = it.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.STARRED
                )

                if (idIndex >= 0 && nameIndex >= 0 &&
                    numberIndex >= 0 && starredIndex >= 0
                ) {
                    while (it.moveToNext()) {
                        val id = it.getString(idIndex)

                        if (contactIds.add(id)) {
                            favourites.add(
                                ContactModel(
                                    id = id,
                                    name = it.getString(nameIndex),
                                    phoneNumber = it.getString(numberIndex),
                                    isFavourite = it.getInt(starredIndex) == 1
                                )
                            )
                        }
                    }
                }
            }
        } catch (_: SecurityException) {
        }

        return favourites
    }

    fun toggleFavourite(context: Context, contact: ContactModel): Boolean {
        val values = ContentValues().apply {
            put(
                ContactsContract.Contacts.STARRED,
                if (contact.isFavourite) 0 else 1
            )
        }

        return try {
            val updatedRows = context.contentResolver.update(
                ContactsContract.Contacts.CONTENT_URI,
                values,
                ContactsContract.Contacts._ID + " = ?",
                arrayOf(contact.id)
            )

            updatedRows > 0
        } catch (_: SecurityException) {
            false
        }
    }

    fun getRecentCalls(context: Context): List<RecentModel> {
        val recentCalls = mutableListOf<RecentModel>()
        val resolver = context.contentResolver

        try {
            val cursor = resolver.query(
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
                val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
                val typeIndex = it.getColumnIndex(CallLog.Calls.TYPE)
                val dateIndex = it.getColumnIndex(CallLog.Calls.DATE)
                val durationIndex = it.getColumnIndex(CallLog.Calls.DURATION)

                if (numberIndex >= 0 && typeIndex >= 0 &&
                    dateIndex >= 0 && durationIndex >= 0
                ) {
                    while (it.moveToNext()) {
                        val phoneNumber = it.getString(numberIndex)
                        val callTypeValue = it.getInt(typeIndex)
                        val callDateValue = it.getLong(dateIndex)
                        val durationValue = it.getLong(durationIndex)

                        recentCalls.add(
                            RecentModel(
                                name = getContactName(context, phoneNumber),
                                phoneNumber = phoneNumber ?: "Unknown",
                                callType = getCallType(callTypeValue),
                                date = formatDate(callDateValue),
                                duration = formatDuration(durationValue)
                            )
                        )
                    }
                }
            }
        } catch (_: SecurityException) {
        }

        return recentCalls
    }

    private fun getContactName(
        context: Context,
        phoneNumber: String?
    ): String {
        if (phoneNumber.isNullOrEmpty()) {
            return "Unknown"
        }

        val lookupUri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )

        return try {
            val cursor = context.contentResolver.query(
                lookupUri,
                arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME),
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
                        return it.getString(nameIndex) ?: "Unknown"
                    }
                }
            }

            "Unknown"
        } catch (_: SecurityException) {
            "Unknown"
        }
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
