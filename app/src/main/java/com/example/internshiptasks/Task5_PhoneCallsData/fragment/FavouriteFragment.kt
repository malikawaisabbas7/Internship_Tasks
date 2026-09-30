package com.example.internshiptasks.Task5_PhoneCallsData

import android.Manifest
import com.example.internshiptasks.R
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

class FavouriteFragment : Fragment() {

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var recyclerViewFavourite: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_favourite_task5,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewFavourite =
            view.findViewById(R.id.recyclerViewFavourite)

        setupRecyclerView()

    loadFavouriteContacts()
    }

    private fun setupRecyclerView() {

        contactAdapter = ContactAdapter(emptyList()) { contact ->

        }

        recyclerViewFavourite.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerViewFavourite.adapter = contactAdapter
    }



    private fun loadFavouriteContacts() {

        val favouriteContacts = mutableListOf<ContactModel>()

        val contactIds = mutableSetOf<String>()

        val cursor = requireContext().contentResolver.query(
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

            while (it.moveToNext()) {

                val id = it.getString(idIndex)

                if (contactIds.add(id)) {

                    val name = it.getString(nameIndex)
                    val phoneNumber = it.getString(numberIndex)
                    val isFavourite = it.getInt(starredIndex) == 1

                    favouriteContacts.add(
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

        contactAdapter.updateList(favouriteContacts)
    }


    override fun onResume() {
        super.onResume()

        if (
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            loadFavouriteContacts()
        }
    }
}