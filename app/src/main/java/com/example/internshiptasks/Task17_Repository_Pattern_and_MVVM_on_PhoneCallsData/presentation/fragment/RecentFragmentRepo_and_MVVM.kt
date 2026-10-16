
package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.adapter.CallLogAdapter
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.viewmodel.RecentCallsViewModel

class RecentFragmentRepo_and_MVVM : Fragment(
    R.layout.fragment_recentrepo_and_mvvm_task17) {

    private val viewModel: RecentCallsViewModel by viewModels()

    private lateinit var recyclerViewRecent: RecyclerView
    private lateinit var callLogAdapter: CallLogAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewRecent =
            view.findViewById(R.id.recyclerViewRecent)

        callLogAdapter = CallLogAdapter()

        recyclerViewRecent.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerViewRecent.adapter = callLogAdapter

        observeViewModel()

        viewModel.loadRecentCalls()
    }

    private fun observeViewModel() {
        viewModel.recentCalls.observe(viewLifecycleOwner) { calls ->
            callLogAdapter.updateData(calls)
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
