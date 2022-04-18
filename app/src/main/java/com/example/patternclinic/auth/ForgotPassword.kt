package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.databinding.ActivityForgotPasswordBinding
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPassword : BaseActivity() {
    companion object {
        lateinit var binding: ActivityForgotPasswordBinding
    }

    val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_forgot_password)
//        val submit = findViewById<TextView>(R.id.tv_submit_forgot)
//        submit.setOnClickListener {
//            startActivity(Intent(this, ResetPassword::class.java))
//        }
    }

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        binding.viewModel = forgotPasswordViewModel
        binding.ivBackForgot.setOnClickListener {
            finish()
        }

    }
}