package com.example.patternclinic.auth.createProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.model.BasicResponse
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.databinding.ActivitySignupBinding
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import com.example.patternclinic.utils.checkEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(val mainRepository: MainRepository) : BaseViewModel() {
    var signUpResponse = MutableLiveData<BasicResponse>()
    var otpResponse = MutableLiveData<LoginResponse>()
    var binding: ActivitySignupBinding? = null


    /**
     * checks for fields in sign-up process
     */
    fun validation(): Boolean {
       if(binding!!.etEmail.text.trim().toString().isEmpty()){
           binding!!.etEmail.error=binding!!.root.context.getString(R.string.enter_email)
           return false
       }
        if(!checkEmail(binding!!.etEmail.text.trim().toString())){
            binding!!.etEmail.error=binding!!.root.context.getString(R.string.enter_valid_email)
            return false
        }
        if(binding!!.etPassword.text.trim().toString().isEmpty()){
            binding!!.etPassword.error=binding!!.root.context.getString(R.string.enter_password)
            return false
        }

        if(binding!!.etConfirmPassword.text.trim().toString().isEmpty()){
            binding!!.etConfirmPassword.error=binding!!.root.context.getString(R.string.enter_confirm_password)
            return false
        }
        if(binding!!.etConfirmPassword.text.trim().toString()!=binding!!.etPassword.text.trim().toString()){
            binding!!.etConfirmPassword.error=binding!!.root.context.getString(R.string.password_match)
            return false
        }

        return true

    }




    /**
     * below method used for signup process
     */

    fun signUpProcess(map: HashMap<String, Any>) {
        showLoader.value = true
        job = viewModelScope.launch {
            val result = getResult({ mainRepository.signUp(map) }, "signUp")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as BasicResponse)

                    signUpResponse.postValue(response)
                }
                is ResponseResult.ERROR -> {
                    errorMessage.postValue(result.result.errorMsg.toString())
                }
                is ResponseResult.FAILURE -> {
                    errorMessage.postValue(result.toString())

                }
            }
            showLoader.value = false
        }
    }

    /**
     * below method used for otp process
     */

    fun sendOtp(map: HashMap<String, Any>) {
        showLoader.value = true
        job = viewModelScope.launch {
            val result = getResult({ mainRepository.sendOtp(map) }, "sendOtp")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as LoginResponse)

                    otpResponse.postValue(response)
                }
                is ResponseResult.ERROR -> {
                    errorMessage.postValue(result.result.errorMsg.toString())
                }
                is ResponseResult.FAILURE -> {
                    errorMessage.postValue(result.toString())

                }
            }
            showLoader.value = false
        }
    }


}