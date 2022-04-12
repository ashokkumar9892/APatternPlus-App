package com.example.patternclinic.selectTeam.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.FragmentSelectTeamBinding
import com.example.patternclinic.selectTeam.SelectTeam2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SelectTeamFragment : BottomSheetDialogFragment() {
    lateinit var bindingFragment: FragmentSelectTeamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_team, container, false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindingFragment.llTeam.setOnClickListener {
            startActivity(Intent(requireContext(), SelectTeam2::class.java))
        }
    }


}