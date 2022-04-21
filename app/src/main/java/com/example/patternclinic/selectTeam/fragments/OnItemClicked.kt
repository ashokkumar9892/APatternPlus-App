package com.example.patternclinic.selectTeam.fragments

import com.example.patternclinic.data.model.DoctorInfo

interface OnItemClicked {
    fun onClicked(it : DoctorInfo)
}