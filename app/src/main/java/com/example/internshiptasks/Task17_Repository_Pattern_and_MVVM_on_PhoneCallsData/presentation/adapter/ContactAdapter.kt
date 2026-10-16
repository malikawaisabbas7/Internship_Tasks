
package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.model.ContactModel

class ContactAdapter(
    private val onFavouriteClick: (ContactModel) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val contactList = mutableListOf<ContactModel>()

    class ContactViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvContactName: TextView =
            itemView.findViewById(R.id.tvContactName)

        val tvContactNumber: TextView =
            itemView.findViewById(R.id.tvContactNumber)

        val btnFavourite: ImageView =
            itemView.findViewById(R.id.btnFavourite)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
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
        val contact = contactList[position]

        holder.tvContactName.text = contact.name
        holder.tvContactNumber.text = contact.phoneNumber

        val starIcon = if (contact.isFavourite) {
            android.R.drawable.btn_star_big_on
        } else {
            android.R.drawable.btn_star_big_off
        }

        holder.btnFavourite.setImageResource(starIcon)


        holder.btnFavourite.setOnClickListener {
            onFavouriteClick(contact)
        }
    }

    override fun getItemCount(): Int = contactList.size

    fun updateList(newContacts: List<ContactModel>) {
        contactList.clear()
        contactList.addAll(newContacts)
        notifyDataSetChanged()
    }
}
