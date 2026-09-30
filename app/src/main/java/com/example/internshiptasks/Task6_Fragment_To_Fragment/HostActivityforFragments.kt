package com.example.internshiptasks.Task6_Fragment_To_Fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.internshiptasks.R


class HostActivityforFragments : AppCompatActivity(){

    private lateinit var fragmentA: FragmentA
    private lateinit var fragmentB: FragmentB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_host_for_fragments_task6)

        if (savedInstanceState == null) {

            fragmentA = FragmentA()
            fragmentB = FragmentB()

            val communicationHandler =
                FragmentCommunicationHandler(
                    fragmentA, fragmentB
                )
            fragmentA.setListener(communicationHandler)
            fragmentB.setListener(communicationHandler)


                    supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentAContainer,
                    fragmentA
                )
                .replace(
                    R.id.fragmentBContainer,
                    fragmentB
                )
                .commit()

        } else {

            fragmentA = supportFragmentManager
                .findFragmentById(R.id.fragmentAContainer) as FragmentA

            fragmentB = supportFragmentManager
                .findFragmentById(R.id.fragmentBContainer) as FragmentB

            val communicationHandler=
                FragmentCommunicationHandler(
                    fragmentA,fragmentB
                )
            fragmentA.setListener(communicationHandler)
            fragmentB.setListener(communicationHandler)
        }
    }


}