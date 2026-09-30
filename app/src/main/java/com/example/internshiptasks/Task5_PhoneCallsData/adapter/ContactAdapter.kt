package com.example.internshiptasks.Task5_PhoneCallsData.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task5_PhoneCallsData.ContactModel

class ContactAdapter(
    private var contactList: List<ContactModel>,
    private val onFavouriteClick: (ContactModel) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvContactName: TextView =
            itemView.findViewById(R.id.tvContactName)

        private val tvContactNumber: TextView =
            itemView.findViewById(R.id.tvContactNumber)

        private val btnFavourite: ImageButton =
            itemView.findViewById(R.id.btnFavourite)

        fun bind(contact: ContactModel) {

            tvContactName.text = contact.name
            tvContactNumber.text = contact.phoneNumber

            if (contact.isFavourite) {
                btnFavourite.setImageResource(
                    android.R.drawable.btn_star_big_on
                )
            } else {
                btnFavourite.setImageResource(
                    android.R.drawable.btn_star_big_off
                )
            }

            btnFavourite.setOnClickListener {
                onFavouriteClick(contact)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_contact_task5,
                parent,
                false
            )

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        holder.bind(contactList[position])
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    fun updateList(newList: List<ContactModel>) {
        contactList = newList
        notifyDataSetChanged()
    }
}