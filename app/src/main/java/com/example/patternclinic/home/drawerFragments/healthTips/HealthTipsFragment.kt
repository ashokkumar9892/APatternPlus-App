package com.example.patternclinic.home.drawerFragments.appointments.healthTips

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.FragmentHealthTipsBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.home.drawerFragments.healthTips.HealthTipDetailActivity
import com.example.patternclinic.utils.Keys

class HealthTipsFragment : Fragment() {

    private lateinit var bindingFragment: FragmentHealthTipsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_health_tips, container, false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindingFragment.ivMenu.setOnClickListener {
            (activity as HomeScreenActivity).binding!!.drawerLayout.openDrawer((activity as HomeScreenActivity).binding!!.sideBar)
        }
        bindingFragment.cv1.setOnClickListener {
            startActivity(Intent(requireContext(),HealthTipDetailActivity::class.java).putExtra(Keys.FROM_CARD_ONE,"cv1"))
        }
        bindingFragment.cv2.setOnClickListener {
            startActivity(Intent(requireContext(),HealthTipDetailActivity::class.java).putExtra(Keys.FROM_CARD_TWO,"cv2"))
        }
    }


}