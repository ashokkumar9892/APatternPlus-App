package com.example.patternclinic.home.drawerFragments.appointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.data.model.AppointmentInfo
import com.example.patternclinic.databinding.ActivityCoachProfileBinding
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.dateConvert_7
import com.google.gson.Gson

class CoachProfileActivity : AppCompatActivity() {
    var binding: ActivityCoachProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coach_profile)
        if (intent.hasExtra(Keys.APPOINTMENT_DETAIL)) {
            val data = Gson().fromJson(
                intent.getStringExtra(Keys.APPOINTMENT_DETAIL),
                AppointmentInfo::class.java
            )
            setData(data)

        }
        initDesign()
    }

    private fun setData(data: AppointmentInfo?) {

        binding!!.tvName.text = data?.doctorName ?: ""
        binding!!.tvTime.text = data?.appointmentTime ?: ""
        binding!!.tvDate.text = dateConvert_7(data!!.appointmentDate)
        Glide.with(this).load(data.doctorPic ?: "")
            .placeholder(R.drawable.coach_img).into(binding!!.ivImage)
        binding!!.tvType.text=data.appointmentType?:""
    }

    private fun initDesign() {
        binding!!.toolBarCoachProfile.tvTitle.text = getString(R.string.coach_profile)
        binding!!.toolBarCoachProfile.ivBack.visibility = View.VISIBLE
        binding!!.toolBarCoachProfile.ivBack.setOnClickListener {
            finish()
        }
    }
}