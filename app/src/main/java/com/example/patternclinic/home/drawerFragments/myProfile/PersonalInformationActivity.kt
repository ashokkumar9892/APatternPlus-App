package com.example.patternclinic.home.drawerFragments.myProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.ActivityPersonalInformationBinding
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.dateConvert_5
import com.example.patternclinic.utils.dateConvert_6
import com.example.patternclinic.utils.dateConvert_6to
import java.lang.Exception

class PersonalInformationActivity : AppCompatActivity() {
    lateinit var binding: ActivityPersonalInformationBinding
    val userDetail by lazy {
        SharedPrefs.getLoggedInUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_information)
        initDesign()
    }

    private fun initDesign() {
        binding
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.personal_information)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
        setData(userDetail)
    }

    private fun setData(it: LoginResponse?) {
        binding.tvName.text =
            "${it?.patientInfo?.firstName ?: ""} ${it?.patientInfo?.lastName ?: ""}"
        binding.tvCountry.text = "${it?.patientInfo?.country ?: ""}"

        binding.tvDob.text =
            if (!it!!.patientInfo.dob.isNullOrEmpty()) dateConvert_6to(it!!.patientInfo.dob!!) else "-"

        binding.tvEmail.text = it?.patientInfo?.email ?: ""


    }
}