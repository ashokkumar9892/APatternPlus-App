package com.example.patternclinic.home.drawerFragments.appointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityAddAppointmentBinding

class AddAppointmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddAppointmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_appointment)
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
                    }
                }
            }
        })
    }
}