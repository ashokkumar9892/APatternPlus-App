package com.example.patternclinic.home.drawerFragments.appointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.auth.CreateProfile.Companion.activity
import com.example.patternclinic.data.model.Currentteam
import com.example.patternclinic.databinding.ActivityAddAppointmentBinding
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.showToast
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAppointmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddAppointmentBinding
    val viewModel: AddAppointmentViewModel by viewModels()

    companion object {
        var team: Currentteam? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_appointment)
        viewModel.addBinding = binding
        binding.viewModel = viewModel
        if (intent.hasExtra(Keys.CURRENT_TEAM)) {
             team =
                Gson().fromJson(intent.getStringExtra(Keys.CURRENT_TEAM), Currentteam::class.java)
            binding.tvCoachName.text = team!!.coachName ?: ""


        }
        setObservers()
        initDesign()
    }

    private fun initDesign() {
        binding.toolBar.ivBack.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.add_appointment)
        binding.toolBar.ivBack.setOnClickListener {
            finish()
        }
        binding.rgGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {

                when (p1) {
                    R.id.rb_virtual -> {
                        binding.llClinicContainer.visibility = View.GONE
                    }
                    R.id.rb_in_person -> {
                        binding.llClinicContainer.visibility = View.VISIBLE
                        binding.tvLocation.text= team!!.teamLocation?:""
                    }
                }
            }
        })
    }

    private fun setObservers() {

        /**
         * observer for loader
         */
        viewModel.showLoader.observe(this)
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
        viewModel.errorMessage.observe(this)
        {
            showToast(it)
        }
        /**
         * observer for failure-response
         */
        viewModel.onFailure.observe(this)
        {
            showToast(it.toString())
        }
        viewModel.createAppointmentResponse.observe(this) {
            if (it.response == 1) {
                showToast(it.errorMessage)
                finish()
            } else {
                showToast(it.errorMessage)
            }
        }
    }
}