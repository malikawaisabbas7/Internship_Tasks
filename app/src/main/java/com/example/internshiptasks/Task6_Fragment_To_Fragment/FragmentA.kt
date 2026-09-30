package com.example.internshiptasks.Task6_Fragment_To_Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.internshiptasks.R
import com.example.internshiptasks.Task6_Fragment_To_Fragment.OnValueSubmitListener

class FragmentA : Fragment() {

    private var listener: OnValueSubmitListener? = null

    fun setListener(listener: OnValueSubmitListener) {

        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_a_task6,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.findViewById<EditText>(R.id.etFragmentA)
        val button = view.findViewById<Button>(R.id.btnSubmitA)

        button.setOnClickListener {

            val value = editText.text.toString()

            listener?.onValueFromFragmentA(value)
        }
    }

    fun showValue(value: String) {

        val editText = view?.findViewById<EditText>(R.id.etFragmentA)

        editText?.setText(value)
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }
}