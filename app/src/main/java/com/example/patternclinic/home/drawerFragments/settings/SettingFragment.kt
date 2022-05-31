package com.example.patternclinic.home.drawerFragments.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.patternclinic.R
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.databinding.FragmentSettingBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.home.drawerFragments.settings.*
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    var onOff = true
    private lateinit var bindingFragment: FragmentSettingBinding
    val viewModel: SettingFragmentViewModel by viewModels()
    val map by lazy {
        HashMap<String, Any>()
    }
    val userDetail by lazy {
        SharedPrefs.getLoggedInUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        setObservers()
        return bindingFragment.root
    }

    private fun setObservers() {
        viewModel.showLoader.observe(requireActivity()) {
            if (it) {
                bindingFragment.loader.visibility = View.VISIBLE
            } else {
                bindingFragment.loader.visibility = View.GONE
            }
        }

        /**
         * observer for error-response
         */
        viewModel.errorMessage.observe(requireActivity())
        {
            activity?.showToast(it)
        }
        /**
         * observer for failure-response
         */
        viewModel.onFailure.observe(requireActivity())
        {
            activity?.showToast(it.toString())
        }

        viewModel.notificationResponse.observe(requireActivity()) {
            activity?.showToast(it.errorMessage)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bindingFragment.ivMenu.setOnClickListener {
            (activity as HomeScreenActivity).binding!!.drawerLayout.openDrawer((activity as HomeScreenActivity).binding!!.sideBar)
        }
        bindingFragment.ivOnOff.setOnClickListener {
            if (!onOff) {
                onOff = true
                bindingFragment.ivOnOff.setImageResource(R.drawable.ic_on_notifiaction)
                map.clear()
                map[ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail!!.authToken
                map[ApiConstants.APIParams.SK.value] = userDetail!!.patientInfo.sk
                map[ApiConstants.APIParams.IS_NOTIFICATION_ON.value] = onOff.toString()
                viewModel.manageNotification(map)
            } else {
                onOff = false
                bindingFragment.ivOnOff.setImageResource(R.drawable.ic_off_notifiaction)
                map.clear()
                map[ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail!!.authToken
                map[ApiConstants.APIParams.SK.value] = userDetail!!.patientInfo.sk
                map[ApiConstants.APIParams.IS_NOTIFICATION_ON.value] = onOff.toString()
                viewModel.manageNotification(map)
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