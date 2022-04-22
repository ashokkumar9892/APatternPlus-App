package com.example.patternclinic.auth

import android.bluetooth.le.BluetoothLeScanner
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.databinding.ActivityLoginBinding
import com.example.patternclinic.utils.changeStatusBarColor
import com.example.patternclinic.utils.showToast
import com.google.android.material.button.MaterialButton
import com.inuker.bluetooth.library.search.BluetoothSearchManager
import com.inuker.bluetooth.library.search.SearchResult
import com.inuker.bluetooth.library.search.response.SearchResponse
import com.veepoo.protocol.VPOperateManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    val loginViewModel: LoginViewModel by viewModels()

    companion object {
        lateinit var binding: ActivityLoginBinding
    }


    override fun binding() {
        changeStatusBarColor(R.color.color_primary_with_opacity_8)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }
        binding.ivEyeLogin.setOnClickListener {
            if (binding.etPassword.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.ivEyeLogin.setImageResource(R.drawable.ic_eye_show)
                binding.etPassword.setSelection(binding.etPassword.text.trim().length)
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivEyeLogin.setImageResource(R.drawable.ic_eye_off)
                binding.etPassword.setSelection(binding.etPassword.text.trim().length)
            }
        }

    }
}