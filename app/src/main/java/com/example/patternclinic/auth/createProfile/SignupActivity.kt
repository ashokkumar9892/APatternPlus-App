package com.example.patternclinic.auth.createProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.auth.LoginActivity
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.databinding.ActivitySignupBinding
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.example.patternclinic.utils.changeStatusBarColor

class SignupActivity : BaseActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        changeStatusBarColor(R.color.color_primary_with_opacity_8)
        clicks()
    }

    private fun clicks() {

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, ConnectDeviceActivity::class.java))
        }
        binding.tvLogIn.setOnClickListener {
            finish()
        }
    }
}