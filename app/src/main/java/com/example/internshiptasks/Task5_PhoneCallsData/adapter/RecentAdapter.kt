package com.example.internshiptasks.Task5_PhoneCallsData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R

class RecentAdapter(
    private var recentList: List<RecentModel>
) : RecyclerView.Adapter<RecentAdapter.RecentViewHolder>() {

    inner class RecentViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvRecentName: TextView =
            itemView.findViewById(R.id.tvRecentName)

        private val tvRecentNumber: TextView =
            itemView.findViewById(R.id.tvRecentNumber)

        private val tvCallType: TextView =
            itemView.findViewById(R.id.tvCallType)

        private val tvCallDate: TextView =
            itemView.findViewById(R.id.tvCallDate)

        private val tvCallDuration: TextView =
            itemView.findViewById(R.id.tvCallDuration)

        fun bind(recent: RecentModel) {

            tvRecentName.text = recent.name
            tvRecentNumber.text = recent.phoneNumber
            tvCallType.text = recent.callType
            tvCallDate.text = recent.date
            tvCallDuration.text =
                "Duration: ${recent.duration}"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_recent_task5,
                parent,
                false
            )

        return RecentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecentViewHolder,
        position: Int
    ) {

        val recent = recentList[position]

        holder.bind(recent)
    }

    override fun getItemCount(): Int {
        return recentList.size
    }

    fun updateList(newList: List<RecentModel>) {

        recentList = newList
        notifyDataSetChanged()
    }
}