package com.example.patternclinic.home.drawerFragments.appointments.personalMetrics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.FragmentPersonalMetricesBinding
import com.example.patternclinic.home.HomeScreenActivity

class PersonalMetricsFragment : Fragment() {

    private lateinit var bindingFragment: FragmentPersonalMetricesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_personal_metrices, container, false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bindingFragment.ivMenu.setOnClickListener {
            (activity as HomeScreenActivity).binding!!.drawerLayout.openDrawer((activity as HomeScreenActivity).binding!!.sideBar)
        }
    }


}