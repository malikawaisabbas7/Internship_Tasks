package com.example.internshiptasks.Task5_PhoneCallsData

import android.Manifest
import com.example.internshiptasks.R
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.Task5_PhoneCallsData.adapter.ContactAdapter

class ContactsFragment : Fragment() {

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var recyclerViewContacts: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_contacts_task5,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewContacts =
            view.findViewById(R.id.recyclerViewContacts)

        setupRecyclerView()

        loadContacts()

    }

    private fun setupRecyclerView() {

        contactAdapter = ContactAdapter(emptyList()) { contact ->

            toggleFavourite(contact)
        }

        recyclerViewContacts.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerViewContacts.adapter = contactAdapter
    }


    private fun loadContacts() {

        val contacts = mutableListOf<ContactModel>()

        val cursor = requireContext().contentResolver.query(
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

            val contactIds = mutableSetOf<String>()

            while (it.moveToNext()) {

                val id = it.getString(idIndex)

                if (contactIds.add(id)) {

                    val name = it.getString(nameIndex)
                    val phoneNumber = it.getString(numberIndex)
                    val isFavourite = it.getInt(starredIndex) == 1

                    contacts.add(
                        ContactModel(
                            id = id,
                            name = name,
                            phoneNumber = phoneNumber,
                            isFavourite = isFavourite
                        )
                    )
                }
            }
        }

        contactAdapter.updateList(contacts)
    }

    private fun toggleFavourite(contact: ContactModel) {

        if (
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }

        val newFavouriteValue = if (contact.isFavourite) {
            0
        } else {
            1
        }

        val values = ContentValues().apply {

            put(
                ContactsContract.Contacts.STARRED,
                newFavouriteValue
            )
        }

        requireContext().contentResolver.update(
            ContactsContract.Contacts.CONTENT_URI,
            values,
            ContactsContract.Contacts._ID + " = ?",
            arrayOf(contact.id)
        )

        loadContacts()
    }

}