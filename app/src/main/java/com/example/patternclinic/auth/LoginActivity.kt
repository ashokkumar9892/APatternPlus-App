package com.example.patternclinic.auth

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.databinding.ActivityLoginBinding
import com.example.patternclinic.utils.changeStatusBarColor
import com.example.patternclinic.utils.openChromeTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        lateinit var binding: ActivityLoginBinding

    }


    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        initDesign()
    }

    private fun initDesign() {
        binding.tvSignUp.setOnClickListener {
            openChromeTab(ApiConstants.SIGN_UP_URL)
//            startActivity(Intent(this,CreateProfile::class.java).putExtra(Keys.NEW_USER,"1"))
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(ApiConstants.SIGN_UP_URL))
//            startActivity(browserIntent)
//            CustomTabsIntent.Builder().build().launchUrl(this,Uri.parse(ApiConstants.SIGN_UP_URL))
        }
        changeStatusBarColor(R.color.color_primary_with_opacity_8)

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }
        binding.ivEyeLogin.setOnClickListener {
            if (binding.etPassword.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
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