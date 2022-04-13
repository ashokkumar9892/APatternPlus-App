package com.example.patternclinic.home.drawerFragments.appointments.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.FragmentSettingBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.home.drawerFragments.settings.*


class SettingFragment : Fragment() {
    var onOff = true
    private lateinit var bindingFragment: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bindingFragment.ivMenu.setOnClickListener {
            (activity as HomeScreenActivity).binding!!.drawerLayout.openDrawer((activity as HomeScreenActivity).binding!!.sideBar)
        }
        bindingFragment.ivOnOff.setOnClickListener {
            if (!onOff) {
                onOff = true
                bindingFragment.ivOnOff.setImageResource(R.drawable.ic_on_notifiaction)
            } else {
                onOff = false
                bindingFragment.ivOnOff.setImageResource(R.drawable.ic_off_notifiaction)
            }
        }
        bindingFragment.llChangePassword.setOnClickListener {
            startActivity(Intent(requireContext(), ChangePassword::class.java))
        }
        bindingFragment.llContactUs.setOnClickListener {
            startActivity(Intent(requireContext(), ContactUsActivity::class.java))
        }
        bindingFragment.llDeviceManagement.setOnClickListener {
            startActivity(Intent(requireContext(), DeviceManagementActivity::class.java))
        }
        bindingFragment.llAboutUs.setOnClickListener {
            startActivity(Intent(requireContext(), AboutUsActivity::class.java))
        }
        bindingFragment.llTermAndPolicies.setOnClickListener {
            startActivity(Intent(requireContext(), TermPoliciesActivity::class.java))
        }
    }


}