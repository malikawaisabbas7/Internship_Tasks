package com.example.internshiptasks.Task15_Singleton_Pattern_on_PhoneCallsData

import com.example.internshiptasks.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

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
        val recentCalls = PhoneCallsData.getRecentCalls(requireContext())
        recentAdapter.updateList(recentCalls)
    }

    override fun onResume() {
        super.onResume()

        if (::recentAdapter.isInitialized){
            loadRecentCalls()
        }


    }

}