package com.example.patternclinic.selectTeam.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.DoctorInfo
import com.example.patternclinic.databinding.FragmentSelectTeamBinding
import com.example.patternclinic.selectTeam.SelectPatternPlusTeam
import com.example.patternclinic.selectTeam.SelectTeam2
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectTeamFragment : BottomSheetDialogFragment(), OnItemClicked {

    val map = HashMap<String, Any>()

    companion object {
         var bindingFragment: FragmentSelectTeamBinding ?=null
    }
    val selectTeamViewModel: SelectTeamViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_team, container, false)

        return bindingFragment!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindingFragment!!.viewModel = selectTeamViewModel
        setOberver()
        if (requireArguments().containsKey(Keys.COACHES)) {
            map.clear()
            map[ApiConstants.APIParams.AUTH_TOKEN.value] = SharedPrefs.getLoggedInUser()!!.authToken
            selectTeamViewModel.coachListApi(map)
        } else {
            map.clear()
            map[ApiConstants.APIParams.AUTH_TOKEN.value] = SharedPrefs.getLoggedInUser()!!.authToken
            selectTeamViewModel.providerListApi(map)
        }

    }

    private fun setOberver() {
        selectTeamViewModel.coachList.observe(this) {
            setAdapter(it.doctorInfo)
        }

        selectTeamViewModel.providerList.observe(this) {
            setAdapter(it.doctorInfo)
        }


    }

    private fun setAdapter(doctorInfo: List<DoctorInfo>?) {
        bindingFragment!!.rvCoachProvList.adapter = CoachProviderListAdapter(doctorInfo!!, this)
    }

    override fun onClicked(it: DoctorInfo) {
        if (requireArguments().containsKey(Keys.COACHES)) {

            SelectPatternPlusTeam.binding.tvCoachName.text = it.userName
            SelectPatternPlusTeam.binding.rvCoachReselect.visibility = View.VISIBLE
            Glide.with(requireContext()).load(it.profileImage)
                .placeholder(R.drawable.ic_dummy_coach)
                .into(SelectPatternPlusTeam.binding.ivCoach)
            SelectPatternPlusTeam.coachInfo=it
            dialog!!.dismiss()


        } else {
            SelectPatternPlusTeam.binding.tvProviderName.text = it.userName
            SelectPatternPlusTeam.binding.rvProviderReselect.visibility = View.VISIBLE
            Glide.with(requireContext()).load(it.profileImage)
                .placeholder(R.drawable.dummy_provider)
                .into(SelectPatternPlusTeam.binding.ivProvider)
            SelectPatternPlusTeam.doctorInfo=it
            dialog!!.dismiss()
        }
        if (SelectPatternPlusTeam.binding.rvCoachReselect.visibility.equals(View.VISIBLE) && SelectPatternPlusTeam.binding.rvProviderReselect.visibility.equals(
                View.VISIBLE
            )
        ) {
            SelectPatternPlusTeam.binding.btnSubmit.visibility = View.VISIBLE
        }
    }


}