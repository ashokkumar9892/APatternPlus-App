package com.example.patternclinic.auth

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.ResponseWrapper
import com.example.patternclinic.retrofit.getResult
import com.example.patternclinic.utils.MyApplication
import com.example.patternclinic.utils.checkEmail
import com.example.patternclinic.utils.createPartFromString
import com.example.patternclinic.utils.showToast
import com.google.android.exoplayer2.metadata.id3.ApicFrame
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val mainRepository: MainRepository) : BaseViewModel() {
    var loginResponse = MutableLiveData<LoginResponse>()
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var activity = LoginActivity.binding


    fun validate(v: View): Boolean {
        if (email.get().isNullOrEmpty()) {
            activity.etEmail.setError(v.context.getString(R.string.enter_email))
            return false
        }
        if (!checkEmail(email.get().toString())) {
            activity.etEmail.error = v.context.getString(R.string.enter_valid_email)
            return false
        }
        if (password.get().isNullOrEmpty()) {
            activity.etPassword.error = v.context.getString(R.string.enter_password)
            return false
        }
        return true
    }

    fun onClick(v: View) {
        if (validate(v)) {
            var map = HashMap<String, Any>()
            map.put(ApiConstants.APIParams.USERNAME.value, email.get().toString())
            map.put(ApiConstants.APIParams.PASSWORD.value, password.get().toString())
            activity.loader.visibility = View.VISIBLE
            job = viewModelScope.launch {
                var result = getResult({ mainRepository.login(map) }, "loginApi")

                when (result) {
                    is ResponseResult.SUCCESS -> {
                        val response = (result.result.data as LoginResponse)

                        loginResponse.postValue(response)
                        if (response.response == 1) {
                            v.context.showToast(response.errorMessage)
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