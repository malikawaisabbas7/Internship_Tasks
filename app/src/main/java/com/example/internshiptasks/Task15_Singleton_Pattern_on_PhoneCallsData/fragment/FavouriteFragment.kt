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
        val favouriteContacts =
            PhoneCallsData.getFavouriteContacts(requireContext())

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