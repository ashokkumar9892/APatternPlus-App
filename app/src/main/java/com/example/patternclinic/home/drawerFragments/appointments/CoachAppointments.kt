package com.example.patternclinic.home.drawerFragments.appointments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityCoachAppointmentsBinding
import com.example.patternclinic.home.HomeScreenActivity

class CoachAppointments : Fragment() {
    private lateinit var bindingFragment: ActivityCoachAppointmentsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragment =
            DataBindingUtil.inflate(
                inflater,
                R.layout.activity_coach_appointments,
                container,
                false
            )
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bindingFragment.ivMenu.setOnClickListener {
            (activity as HomeScreenActivity).binding!!.drawerLayout.openDrawer((activity as HomeScreenActivity).binding!!.sideBar)
        }
        bindingFragment.llEmmaWatson.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireContext(),
                    CoachProfileActivity::class.java
                )
            )
        }
    }

}