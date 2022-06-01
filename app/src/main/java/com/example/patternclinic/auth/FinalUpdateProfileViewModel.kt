package com.example.patternclinic.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.model.PatientInfo
import com.example.patternclinic.data.model.UpdateProfileResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import com.example.patternclinic.setupDevice.ConnectDeviceActivity
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinalUpdateProfileViewModel @Inject constructor(val mainRepository: MainRepository) :
    BaseViewModel() {
    var updateProfileResponse = MutableLiveData<UpdateProfileResponse>()
    var activity = CreateProfileHeight.binding
    var map = CreateProfileHeight.map

    fun onClick(v: View) {

//        createProfileApi(map)
    }

    fun createProfileApi(map: HashMap<String, Any>) {

        activity.loader.visibility = View.VISIBLE
        job = viewModelScope.launch {
            val result = getResult({ mainRepository.updateProfile(map) }, "updateProfile")

            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as UpdateProfileResponse)

                    updateProfileResponse.postValue(response)

                }
                is ResponseResult.ERROR -> {
//                        errorMessage.postValue(result.result.errorMsg.toString())
                    activity.root.context.showToast(result.result.errorMsg.toString())
                }
                is ResponseResult.FAILURE -> {
                    activity.root.context.showToast(result.toString())
                }
            }
            activity.loader.visibility = View.GONE
        }
    }
}