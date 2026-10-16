
package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.adapter.ContactAdapter
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.viewmodel.ContactsViewModel

class ContactsFragmentRepo_and_MVVM : Fragment(R.layout.fragment_contacts_repo_and_mvvm_task17) {

    private val viewModel: ContactsViewModel by viewModels()

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var recyclerViewContacts: RecyclerView

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewContacts =
            view.findViewById(R.id.recyclerViewContacts)

        setupRecyclerView()
        observeViewModel()

        viewModel.loadContacts()
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter { contact ->
            viewModel.toggleFavourite(contact)
        }

        recyclerViewContacts.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerViewContacts.adapter = contactAdapter
    }

    private fun observeViewModel() {
        viewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            contactAdapter.updateList(contacts)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
