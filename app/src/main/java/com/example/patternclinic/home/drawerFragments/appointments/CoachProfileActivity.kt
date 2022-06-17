package com.example.patternclinic.home.drawerFragments.appointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.data.model.AppointmentInfo
import com.example.patternclinic.databinding.ActivityCoachProfileBinding
import com.example.patternclinic.databinding.DialogVideoCallBinding
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.dateConvert_7
import com.example.patternclinic.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoachProfileActivity : AppCompatActivity() {
    var binding: ActivityCoachProfileBinding? = null
    val viewModel: CoachDetailViewModel by viewModels()

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
        clicks()
    }

    private fun clicks() {

        binding!!.ivVideoCall.setOnClickListener {
            if (viewModel.sdk == null) {
                viewModel.initializeSdk(this)
            }

            val zoomDialog = BottomSheetDialog(this)
            val zoomDialogBinding = DialogVideoCallBinding.inflate(layoutInflater)
            zoomDialog.setContentView(zoomDialogBinding.root)

            zoomDialogBinding.apply {
                tvSubmit.setOnClickListener {
                    if (etMeetingId.text.toString().trim().isEmpty()) {
                        showToast(getString(R.string.enter_id))
                    } else if (etPwd.text.toString().trim().isEmpty()) {
                        showToast(getString(R.string.enter_password))
                    } else {
                        viewModel.joinMeeting(
                            this@CoachProfileActivity,
                            etMeetingId.text.toString().trim(),
                            etPwd.text.toString().trim()
                        )
                    }
                }
            }
            zoomDialog.show()


        }
    }

    private fun setData(data: AppointmentInfo?) {

        binding!!.tvName.text = data?.doctorName ?: ""
        binding!!.tvTime.text = data?.appointmentTime ?: ""
        binding!!.tvDate.text = dateConvert_7(data!!.appointmentDate)
        Glide.with(this).load(data.doctorPic ?: "")
            .placeholder(R.drawable.coach_img).into(binding!!.ivImage)
        binding!!.tvType.text = data.appointmentType ?: ""
    }

    private fun initDesign() {
        binding!!.tvTitle.text = getString(R.string.coach_profile)
        binding!!.ivBack.visibility = View.VISIBLE
        binding!!.ivBack.setOnClickListener {
            finish()
        }
    }

}