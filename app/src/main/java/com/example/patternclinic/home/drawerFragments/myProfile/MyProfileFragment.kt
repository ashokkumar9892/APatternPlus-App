package com.example.patternclinic.home.drawerFragments.appointments.myProfile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.auth.CreateProfileHeight
import com.example.patternclinic.auth.CreateProfileWeight
import com.example.patternclinic.auth.LoginActivity
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.FragmentMyProfileBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.home.drawerFragments.myProfile.CurrentHeightActivity
import com.example.patternclinic.home.drawerFragments.myProfile.CurrentWeightActivity
import com.example.patternclinic.home.drawerFragments.myProfile.PersonalInformationActivity
import com.example.patternclinic.home.drawerFragments.myProfile.YourApTeamActivity
import com.example.patternclinic.selectTeam.SelectTeam2
import com.example.patternclinic.utils.SharedPrefs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MyProfileFragment : Fragment() {
    private lateinit var bindingFragment: FragmentMyProfileBinding
    val userDetail by lazy {
        SharedPrefs.getLoggedInUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        setData(userDetail)
        return bindingFragment.root
    }

    private fun setData(it: LoginResponse?) {

        bindingFragment.tvName.text =
            "${it?.patientInfo?.firstName ?: ""} ${it?.patientInfo?.lastName ?: ""}"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bindingFragment.ivMenu.setOnClickListener {
            (activity as HomeScreenActivity).binding!!.drawerLayout.openDrawer((activity as HomeScreenActivity).binding!!.sideBar)
        }
        bindingFragment.llPersonalInfo.setOnClickListener {
            startActivity(Intent(requireContext(), PersonalInformationActivity::class.java))
        }
        bindingFragment.llCurrentHeight.setOnClickListener {
            startActivity(Intent(requireContext(), CreateProfileHeight::class.java))
        }
        bindingFragment.llWeight.setOnClickListener {
            startActivity(Intent(requireContext(), CreateProfileWeight::class.java))
        }
        bindingFragment.llApTeam.setOnClickListener {
            startActivity(Intent(requireContext(), YourApTeamActivity::class.java))
        }
        bindingFragment.llLogout.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finishAffinity()
        }
    }
}