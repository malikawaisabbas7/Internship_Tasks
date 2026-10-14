package com.example.internshiptasks.Task15_Singleton_Pattern_on_PhoneCallsData

import android.Manifest
import com.example.internshiptasks.R
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.Task15_Singleton_Pattern_on_PhoneCallsData.adapter.ContactAdapter

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
        val contacts = PhoneCallsData.getContacts(requireContext())
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

        val updated = PhoneCallsData.toggleFavourite(
            requireContext(),
            contact
        )

        if (updated) {
            loadContacts()
        }
    }


}