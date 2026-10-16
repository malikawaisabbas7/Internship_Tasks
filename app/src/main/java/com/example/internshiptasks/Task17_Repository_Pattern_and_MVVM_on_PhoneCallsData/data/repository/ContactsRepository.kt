
package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.repository

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.model.ContactModel

class ContactsRepository(private val context: Context) {

    fun getContacts(): List<ContactModel> {
        checkPermission(Manifest.permission.READ_CONTACTS)

        val contacts = mutableListOf<ContactModel>()
        val seenContactIds = mutableSetOf<String>()

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.STARRED
        )

        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )?.use { cursor ->

            val idIndex = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            )
            val nameIndex = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
            val numberIndex = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )
            val starredIndex = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.STARRED
            )

            while (cursor.moveToNext()) {
                val id = cursor.getString(idIndex)

                if (seenContactIds.add(id)) {
                    contacts.add(
                        ContactModel(
                            id = id,
                            name = cursor.getString(nameIndex).orEmpty(),
                            phoneNumber = cursor.getString(numberIndex).orEmpty(),
                            isFavourite = cursor.getInt(starredIndex) == 1
                        )
                    )
                }
            }
        }

        return contacts
    }


    fun getFavouriteContacts(): List<ContactModel> {
        checkPermission(Manifest.permission.READ_CONTACTS)

        val favouriteContacts = mutableListOf<ContactModel>()
        val seenContactIds = mutableSetOf<String>()

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.STARRED
        )

        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            "${ContactsContract.CommonDataKinds.Phone.STARRED} = ?",
            arrayOf("1"),
            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
        )?.use { cursor ->

            val idIndex = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            )
            val nameIndex = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
            val numberIndex = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )
            val starredIndex = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.STARRED
            )

            while (cursor.moveToNext()) {
                val id = cursor.getString(idIndex)

                if (seenContactIds.add(id)) {
                    favouriteContacts.add(
                        ContactModel(
                            id = id,
                            name = cursor.getString(nameIndex).orEmpty(),
                            phoneNumber = cursor.getString(numberIndex).orEmpty(),
                            isFavourite = cursor.getInt(starredIndex) == 1
                        )
                    )
                }
            }
        }

        return favouriteContacts
    }


    fun toggleFavourite(contact: ContactModel) {
        checkPermission(Manifest.permission.WRITE_CONTACTS)

        val newFavouriteValue = if (contact.isFavourite) 0 else 1

        val values = ContentValues().apply {
            put(
                ContactsContract.Contacts.STARRED,
                newFavouriteValue
            )
        }

        val updatedRows = context.contentResolver.update(
            ContactsContract.Contacts.CONTENT_URI,
            values,
            ContactsContract.Contacts._ID + " = ?",
            arrayOf(contact.id)
        )

        if (updatedRows <= 0) {
            throw IllegalStateException("Unable to update favourite status.")
        }
    }

    private fun checkPermission(permission: String) {
        if (
            ContextCompat.checkSelfPermission(context, permission) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw SecurityException("$permission permission is not granted.")
        }
    }
}
