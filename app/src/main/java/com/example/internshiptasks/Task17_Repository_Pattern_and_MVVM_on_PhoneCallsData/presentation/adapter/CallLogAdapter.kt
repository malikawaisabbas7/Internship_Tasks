package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.model.CallLogModel

class CallLogAdapter :
    RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder>() {

    private val callList = mutableListOf<CallLogModel>()

    class CallLogViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvRecentName: TextView =
            itemView.findViewById(R.id.tvRecentName)

        val tvRecentNumber: TextView =
            itemView.findViewById(R.id.tvRecentNumber)

        val tvCallType: TextView =
            itemView.findViewById(R.id.tvCallType)

        val tvCallDate: TextView =
            itemView.findViewById(R.id.tvCallDate)

        val tvCallDuration: TextView =
            itemView.findViewById(R.id.tvCallDuration)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CallLogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recent_task5,
            parent,
            false
        )

        return CallLogViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CallLogViewHolder,
        position: Int
    ) {
        val call = callList[position]

        holder.tvRecentName.text = call.contactName
        holder.tvRecentNumber.text = call.phoneNumber
        holder.tvCallType.text = call.callType
        holder.tvCallDate.text = call.callDate
        holder.tvCallDuration.text = call.callDuration
    }

    override fun getItemCount(): Int = callList.size

    fun updateData(newCalls: List<CallLogModel>) {
        callList.clear()
        callList.addAll(newCalls)
        notifyDataSetChanged()
    }
}
