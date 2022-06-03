package com.example.patternclinic.home.drawerFragments.appointments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.patternclinic.R
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.AppointmentListResponse
import com.example.patternclinic.databinding.ActivityCoachAppointmentsBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.home.drawerFragments.AppointmentAdapter
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoachAppointments : Fragment() {
    private lateinit var binding: ActivityCoachAppointmentsBinding
    val viewModel: AppointmentListViewModel by viewModels()
    val userDetail by lazy { SharedPrefs.getLoggedInUser() }
    var appointmentList: AppointmentListResponse? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.activity_coach_appointments,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) {
                    upcomingAppointments()
                } else {
                    completedAppointments()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.ivMenu.setOnClickListener {
            (activity as HomeScreenActivity).binding!!.drawerLayout.openDrawer((activity as HomeScreenActivity).binding!!.sideBar)
        }
        binding.fabAdd.setOnClickListener {
            if (appointmentList != null) {
                requireActivity().startActivity(
                    Intent(
                        requireContext(),
                        AddAppointmentActivity::class.java
                    ).putExtra(Keys.CURRENT_TEAM, Gson().toJson(appointmentList!!.currentteam))
                )
            }
        }

//        bindingFragment.llEmmaWatson.setOnClickListener {
//            requireActivity().startActivity(
//                Intent(
//                    requireContext(),
//                    CoachProfileActivity::class.java
//                )
//            )
//        }
    }

    override fun onResume() {
        if(binding.tabLayout.selectedTabPosition==0){
            upcomingAppointments()
        }else{
            completedAppointments()
        }
        super.onResume()
    }

    private fun setObservers() {

        /**
         * observer for loader
         */
        viewModel.showLoader.observe(requireActivity())
        {
            if (it) {
                binding.loader.visibility = View.VISIBLE
            } else {
                binding.loader.visibility = View.GONE
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
        viewModel.appointmentListResponse.observe(requireActivity()) {
            if (it.response == 1) {
                appointmentList = it

                    binding.rvAppointments.adapter = AppointmentAdapter(it.appointmentInfo)

            } else {
                activity?.showToast(it.errorMessage)
            }
        }
    }

    private fun upcomingAppointments() {
        val map = HashMap<String, Any>()
        map[ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail!!.authToken
        map[ApiConstants.APIParams.APPOINTMENT_STATUS.value] = Keys.APPOINTMENT_STATUS_SENT
        map[ApiConstants.APIParams.PATIENT_SK.value] = userDetail!!.patientInfo.sk
        viewModel.appointmentList(map)
    }

    private fun completedAppointments() {
        val map = HashMap<String, Any>()
        map[ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail!!.authToken
        map[ApiConstants.APIParams.APPOINTMENT_STATUS.value] = Keys.APPOINTMENT_STATUS_COMPLETE
        map[ApiConstants.APIParams.PATIENT_SK.value] = userDetail!!.patientInfo.sk
        viewModel.appointmentList(map)
    }
}