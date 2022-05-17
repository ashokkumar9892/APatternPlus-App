package com.example.patternclinic.auth

import android.content.Intent
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
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.example.patternclinic.utils.*
import com.google.android.exoplayer2.metadata.id3.ApicFrame
import com.google.android.material.button.MaterialButton
import com.google.firebase.messaging.FirebaseMessaging
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

    init {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            SharedPrefs.saveFcmToken(it.result)
        }
    }


    fun validate(v: View): Boolean {
        if (email.get()?.trim().isNullOrEmpty()) {
            activity.etEmail.setError(v.context.getString(R.string.enter_email))
            return false
        }
        if (!checkEmail(email.get()!!.trim().toString())) {
            activity.etEmail.error = v.context.getString(R.string.enter_valid_email)
            return false
        }
        if (password.get()?.trim().isNullOrEmpty()) {
            activity.etPassword.error = v.context.getString(R.string.enter_password)
            return false
        }
        return true
    }

    fun onClick(v: View) {
        if (validate(v)) {
            var map = HashMap<String, Any>()
            map[ApiConstants.APIParams.USERNAME.value] = email.get()?.trim().toString()
            map[ApiConstants.APIParams.PASSWORD.value] = password.get()?.trim().toString()
            map[ApiConstants.APIParams.DEVICE_TOKEN.value] = SharedPrefs.getFcmToken().toString()
            map[ApiConstants.APIParams.DEVICE_TYPE.value] = Keys.DEVICE_ANDROID
            activity.loader.visibility = View.VISIBLE
            job = viewModelScope.launch {
                var result = getResult({ mainRepository.login(map) }, "loginApi")

                when (result) {
                    is ResponseResult.SUCCESS -> {
                        val response = (result.result.data as LoginResponse)

                        loginResponse.postValue(response)
                        if (response.response == 1) {
                            v.context.showToast(response.errorMessage)
                            SharedPrefs.saveLoggedInUser(response)
//                            v.context.startActivity(
//                                Intent(
//                                    v.context,
//                                    ConnectDeviceActivity::class.java
//                                )
//                            )
                            //remove below line after need
                            v.context.startActivity(Intent(v.context, CreateProfile::class.java))
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