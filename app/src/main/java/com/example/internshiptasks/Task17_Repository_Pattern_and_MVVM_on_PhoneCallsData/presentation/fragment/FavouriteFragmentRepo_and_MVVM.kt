
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
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.viewmodel.FavouriteViewModel

class FavouriteFragmentRepo_and_MVVM :
    Fragment(R.layout.fragment_favourite_repo_and__mvvm_task17) {

    private val viewModel: FavouriteViewModel by viewModels()

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var recyclerViewFavourite: RecyclerView

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewFavourite =
            view.findViewById(R.id.recyclerViewFavourite)

        setupRecyclerView()
        observeViewModel()
        viewModel.loadFavouriteContacts()
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter { contact ->
            viewModel.toggleFavourite(contact)
        }

        recyclerViewFavourite.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerViewFavourite.adapter = contactAdapter
    }

    private fun observeViewModel() {
        viewModel.favouriteContacts.observe(viewLifecycleOwner) { contacts ->
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

    override fun onResume() {
        super.onResume()

        if (::contactAdapter.isInitialized) {
            viewModel.loadFavouriteContacts()
        }
    }
}
