package com.example.patternclinic.auth

import android.content.Intent
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.BasicResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.checkEmail
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(val mainRepository: MainRepository) :
    BaseViewModel() {
    var forgotResponse = MutableLiveData<BasicResponse>()
    var email = ObservableField<String>()
    var activity = ForgotPassword.binding


    fun validate(v: View): Boolean {
        if (email.get().isNullOrEmpty()) {
            activity.etEmail.setError(v.context.getString(R.string.enter_email))
            return false
        }
        if (!checkEmail(email.get().toString())) {
            activity.etEmail.error = v.context.getString(R.string.enter_valid_email)
            return false
        }

        return true
    }

    fun onClick(v: View) {
        if (validate(v)) {
            var map = HashMap<String, Any>()
            map.put(ApiConstants.APIParams.FORGOT_PASSWORD_USER_NAME.value, email.get().toString())

            activity.loader.visibility = View.VISIBLE
            job = viewModelScope.launch {
                var result = getResult({ mainRepository.forgotPassword(map) }, "forgotApi")

                when (result) {
                    is ResponseResult.SUCCESS -> {
                        val response = (result.result.data as BasicResponse)

                        forgotResponse.postValue(response)
                        if (response.response == 1) {
                            v.context.showToast(response.errorMessage)
                            activity.root.context.startActivity(
                                Intent(activity.root.context, ResetPassword::class.java).putExtra(
                                    Keys.RESET_USER_NAME_KEY, email.get()!!.trim()
                                )
                            )
                        } else {
                            v.context.showToast(response.errorMessage)
                        }
                    }
                    is ResponseResult.ERROR -> {
//                        errorMessage.postValue(result.result.errorMsg.toString())
                        v.context.showToast(result.result.errorMsg.toString())
                    }
                    is ResponseResult.FAILURE -> {
                        v.context.showToast(result.toString())
                    }
                }
                activity.loader.visibility = View.GONE
            }
        }
    }

}