package com.example.patternclinic.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.databinding.ActivityResetPasswordBinding
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.example.patternclinic.utils.Keys
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPassword : BaseActivity() {
    val resetPasswordViewModel:ResetPasswordViewModel by viewModels()
    companion object{
        lateinit var binding:ActivityResetPasswordBinding
        lateinit var userName:String
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_reset_password)
//        val submit = findViewById<MaterialButton>(R.id.btn_submit_reset)
//        submit.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
//    }

    override fun binding() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_reset_password)
        binding.viewModel=resetPasswordViewModel
        if(intent.hasExtra(Keys.RESET_USER_NAME_KEY)){
            userName= intent.getStringExtra(Keys.RESET_USER_NAME_KEY)!!
        }
    }
}