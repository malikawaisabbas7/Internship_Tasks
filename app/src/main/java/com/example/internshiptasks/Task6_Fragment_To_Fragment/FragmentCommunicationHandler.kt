package com.example.internshiptasks.Task6_Fragment_To_Fragment

import com.example.internshiptasks.Task6_Fragment_To_Fragment.OnValueSubmitListener

class FragmentCommunicationHandler(
    private val fragmentA: FragmentA,
    private var fragmentB: FragmentB
): OnValueSubmitListener {
    override fun onValueFromFragmentA(value: String){
        fragmentB.showValue(value)
    }

    override fun onValueFromFragmentB(value: String){
        fragmentA.showValue(value)
    }
}