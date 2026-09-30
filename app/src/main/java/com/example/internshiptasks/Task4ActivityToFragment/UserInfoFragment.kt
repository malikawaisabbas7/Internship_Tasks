package com.example.internshiptasks.Task4ActivityToFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.internshiptasks.R

class UserInfoFragment : Fragment() {

    private var listener: OnUserDataSubmitListener? = null

    interface OnUserDataSubmitListener {
        fun onUserDataSubmit(
            name: String,
            phone: String,
            email: String,
            age: String,
            gender: String
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the host Activity implements the interface
        listener = context as? OnUserDataSubmitListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_input_task4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etAge = view.findViewById<EditText>(R.id.etAge)
        val spinnerGender = view.findViewById<Spinner>(R.id.spinnerGender)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)

        setupGenderSpinner(spinnerGender)

        btnSubmit.setOnClickListener {
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val email = etEmail.text.toString()
            val age = etAge.text.toString()
            val gender = spinnerGender.selectedItem.toString()

            listener?.onUserDataSubmit(
                name = name,
                phone = phone,
                email = email,
                age = age,
                gender = gender
            )
        }
    }

    private fun setupGenderSpinner(spinner: Spinner) {
        val genders = arrayOf("Select gender", "Male", "Female")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            genders
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinner.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}