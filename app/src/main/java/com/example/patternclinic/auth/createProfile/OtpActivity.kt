package com.example.patternclinic.auth.createProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.databinding.ActivityOtpBinding
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpActivity : BaseActivity(), View.OnKeyListener {
    lateinit var binding: ActivityOtpBinding
    val viewModel: SignUpViewModel by viewModels()
    var map = HashMap<String, Any>()

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp)
        initViews()
        setObservers()
    }


    fun setObservers() {
        /**
         * observer for sign up response
         */
        viewModel.otpResponse.observe(this) {
            if (it.response == 1) {
                showToast(it.errorMessage)
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

    private fun initViews() {
        /**
         * setup text watchers for automatic focus to next field in while typing otp
         */
        binding.et1.addTextChangedListener {
            if (binding.et1.text.trim().toString().length == 1) {
                binding.et2.requestFocus()
                binding.et1.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.color_primary_with_5)
            }
        }
        /**
         * setup text watchers for automatic focus to next field in while typing otp
         */
        binding.et2.addTextChangedListener {
            if (binding.et2.text.trim().toString().length == 1) {
                binding.et3.requestFocus()
                binding.et2.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.color_primary_with_5)
            } else {
                binding.et1.requestFocus()
                binding.et2.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.black_with_opacity_5)
            }
        }
        /**
         * setup text watchers for automatic focus to next field in while typing otp
         */
        binding.et3.addTextChangedListener {
            if (binding.et3.text.trim().toString().length == 1) {
                binding.et4.requestFocus()
                binding.et3.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.color_primary_with_5)
            } else {
                binding.et2.requestFocus()
                binding.et3.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.black_with_opacity_5)
            }
        }
        /**
         * setup text watchers for automatic focus to next field in while typing otp
         */
        binding.et4.addTextChangedListener {
            if (binding.et4.text.trim().toString().length == 1) {
                binding.et5.requestFocus()
                binding.et4.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.color_primary_with_5)
            } else {
                binding.et3.requestFocus()
                binding.et4.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.black_with_opacity_5)
            }
        }
        binding.et5.addTextChangedListener {
            if (binding.et5.text.trim().toString().length == 1) {
                binding.et6.requestFocus()
                binding.et5.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.color_primary_with_5)
            } else {
                binding.et4.requestFocus()
                binding.et5.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.black_with_opacity_5)
            }
        }
        /**
         * setup text watchers for automatic focus to next field in while typing otp
         */
        binding.et6.addTextChangedListener {
            if (binding.et6.text.trim().toString().isEmpty()) {
                binding.et5.requestFocus()
                binding.et6.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.black_with_opacity_5)
            } else {
                binding.et6.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.color_primary_with_5)
            }
        }
        /**
         * added key listeners for erase otp
         */
        binding.et2.setOnKeyListener(this)
        binding.et3.setOnKeyListener(this)
        binding.et4.setOnKeyListener(this)
        binding.et5.setOnKeyListener(this)
        binding.et6.setOnKeyListener(this)
        /**
         * below clicks defined
         */
        binding.btnSubmit.setOnClickListener {
            if (otpValidation()) {
                map.clear()
                map.putAll(intent.getSerializableExtra(Keys.OTP) as HashMap<String, Any>)
                map[ApiConstants.APIParams.CONFIRMATION_CODE.value] =
                    binding.et1.text.trim().toString() + binding.et2.text.trim()
                        .toString() + binding.et3.text.trim().toString() + binding.et4.text.trim()
                        .toString() + binding.et5.text.trim().toString() + binding.et6.text.trim()
                        .toString()
                viewModel.sendOtp(map)
            }
        }

    }

    /**
     * otp validation check
     */
    fun otpValidation(): Boolean {
        if (binding.et1.text.toString().trim().isEmpty() || binding.et2.text.toString().trim()
                .isEmpty() || binding.et3.text.toString().trim()
                .isEmpty() || binding.et4.text.toString().trim()
                .isEmpty() || binding.et5.text.toString().trim()
                .isEmpty() || binding.et6.text.toString().trim().isEmpty()
        ) {
            showToast(getString(R.string.enter_otp))
            return false
        }
        return true

    }

    /**
     * defined the key listeners for each edittext fields
     */

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        when (p0!!.id) {
            binding.et2.id -> {
                if (p1 == KeyEvent.KEYCODE_DEL) {
                    if (binding.et2.text.trim().toString().isEmpty()) {
                        binding.et1.requestFocus()
                        binding.et2.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.black_with_opacity_5)
                    }
                }
            }
            binding.et3.id -> {
                if (p1 == KeyEvent.KEYCODE_DEL) {
                    if (binding.et3.text.trim().toString().isEmpty()) {
                        binding.et2.requestFocus()
                        binding.et3.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.black_with_opacity_5)
                    }
                }
            }
            binding.et4.id -> {
                if (p1 == KeyEvent.KEYCODE_DEL) {
                    if (binding.et4.text.trim().toString().isEmpty()) {
                        binding.et3.requestFocus()
                        binding.et4.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.black_with_opacity_5)
                    }
                }
            }
            binding.et5.id -> {
                if (p1 == KeyEvent.KEYCODE_DEL) {
                    if (binding.et5.text.trim().toString().isEmpty()) {
                        binding.et4.requestFocus()
                        binding.et5.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.black_with_opacity_5)
                    }
                }
            }
            binding.et6.id -> {
                if (p1 == KeyEvent.KEYCODE_DEL) {
                    if (binding.et6.text.trim().toString().isEmpty()) {
                        binding.et5.requestFocus()
                        binding.et6.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.black_with_opacity_5)
                    }
                }
            }
        }
        return false
    }
}