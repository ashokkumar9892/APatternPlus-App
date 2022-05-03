package com.example.patternclinic.auth.createProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.auth.LoginActivity
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.databinding.ActivitySignupBinding
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.changeStatusBarColor
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivity() {
    lateinit var binding: ActivitySignupBinding
    val viewModel: SignUpViewModel by viewModels()
    var map = HashMap<String, Any>()

    /**
     * onCreate binding ->
     */
    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewModel.binding = binding
        changeStatusBarColor(R.color.color_primary_with_opacity_8)
        clicks()
        setObservers()
    }

    /**
     * setup observers for view_model instances
     */
    fun setObservers() {
        /**
         * observer for sign up response
         */
        viewModel.signUpResponse.observe(this) {
            if (it.response == 1) {
                showToast(it.errorMessage)
                startActivity(Intent(this,OtpActivity::class.java).putExtra(Keys.OTP,map))

            } else {
                showToast(it.errorMessage)
            }
        }

        /**
         * observer for loader
         */
        viewModel.showLoader.observe(this) {
            if (it) {
                binding.loader.visibility = View.VISIBLE
            } else {
                binding.loader.visibility = View.GONE
            }
        }
        /**
         * observer for error-response
         */
        viewModel.errorMessage.observe(this) {
            showToast(it)
        }
        /**
         * observer for failure-response
         */
        viewModel.onFailure.observe(this) {
            showToast(it.toString())
        }
    }

    /**
     * initializing clicks
     */

    private fun clicks() {

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, ConnectDeviceActivity::class.java))
        }
        binding.tvLogIn.setOnClickListener {
            finish()
        }
        /**
         * below click for showing password and hide password
         */
        binding.ivEyePassword.setOnClickListener {
            if (binding.etPassword.transformationMethod.equals(
                    PasswordTransformationMethod.getInstance()
                )
            ) {
                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.ivEyePassword.setImageResource(R.drawable.ic_eye_show)
                binding.etPassword.setSelection(binding.etPassword.text.trim().length)
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivEyePassword.setImageResource(R.drawable.ic_eye_off)
                binding.etPassword.setSelection(binding.etPassword.text.trim().length)
            }

        }
        /**
         * below click for showing confirm password and hide confirm password
         */
        binding.ivEyeConfirm.setOnClickListener {
            if (binding.etConfirmPassword.transformationMethod.equals(
                    PasswordTransformationMethod.getInstance()
                )
            ) {
                binding.etConfirmPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.ivEyeConfirm.setImageResource(R.drawable.ic_eye_show)
                binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.trim().length)
            } else {
                binding.etConfirmPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.ivEyeConfirm.setImageResource(R.drawable.ic_eye_off)
                binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.trim().length)
            }
        }
        /**
         * below click for sign up process
         */
        binding.btnSignUp.setOnClickListener {
            if (viewModel.validation()) {
                map.clear()
                map[ApiConstants.APIParams.USERNAME.value] = binding.etEmail.text.trim().toString()
                map[ApiConstants.APIParams.PASSWORD.value] =
                    binding.etPassword.text.trim().toString()
                viewModel.signUpProcess(map)
            }
        }
    }
}