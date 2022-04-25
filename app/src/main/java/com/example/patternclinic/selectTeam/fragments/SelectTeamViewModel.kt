package com.example.patternclinic.selectTeam.fragments

import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.auth.CreateProfile
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.CoachProviderListResponse
import com.example.patternclinic.data.model.DoctorInfo
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import com.example.patternclinic.selectTeam.SelectPatternPlusTeam
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class SelectTeamViewModel @Inject constructor(val mainRepository: MainRepository) :
    BaseViewModel() {
    var coachList = MutableLiveData<CoachProviderListResponse>()
    var providerList = MutableLiveData<CoachProviderListResponse>()
    var selectTeamModel=MutableLiveData<ResponseBody>()
    var binding = SelectTeamFragment.bindingFragment
    var activityBinding = SelectPatternPlusTeam.binding
//    init {
//        val map=HashMap<String,Any>()
//        map.put(ApiConstants.APIParams.AUTH_TOKEN.value,SharedPrefs.getLoggedInUser()!!.authToken)
//        coachListApi(map)
//    }


    fun coachListApi(map: HashMap<String, Any>) {
        binding.loader.visibility = View.VISIBLE
        job = viewModelScope.launch {
            val result = getResult({ mainRepository.getCoachList(map) }, "getCoachList")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as CoachProviderListResponse)

                    coachList.postValue(response)
                    if (response.response == 1) {
//                        binding.root.context.showToast(response.errorMessage)
//                        setAdapter(response.doctorInfo)

                    } else {
                        binding.root.context.showToast(response.errorMessage)
                    }
                }
                is ResponseResult.ERROR -> {
//                        errorMessage.postValue(result.result.errorMsg.toString())
                    binding.root.context.showToast(result.result.errorMsg.toString())
                }
                is ResponseResult.FAILURE -> {
//                    binding.root.context.showToast(result.toString())
                }
            }
            binding.loader.visibility = View.GONE
        }
    }

    fun providerListApi(map: HashMap<String, Any>) {
        binding.loader.visibility = View.VISIBLE
        job = viewModelScope.launch {
            val result = getResult({ mainRepository.getDoctorList(map) }, "getDoctorApi")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as CoachProviderListResponse)

                    coachList.postValue(response)
                    if (response.response == 1) {
//                        binding.root.context.showToast(response.errorMessage)

                    } else {
                        binding.root.context.showToast(response.errorMessage)
                    }
                }
                is ResponseResult.ERROR -> {
//                        errorMessage.postValue(result.result.errorMsg.toString())
                    binding.root.context.showToast(result.result.errorMsg.toString())
                }
                is ResponseResult.FAILURE -> {
//                    binding.root.context.showToast(result.toString())
                }
            }
            binding.loader.visibility = View.GONE
        }
    }

    fun selectTeamApi(map: HashMap<String, Any>) {
        activityBinding.loader.visibility = View.VISIBLE
        job = viewModelScope.launch {
            var result = getResult({ mainRepository.selectApTeam(map) }, "selectApTeamApi")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as CoachProviderListResponse)

                    coachList.postValue(response)
                    if (response.response == 1) {
//                        binding.root.context.showToast(response.errorMessage)

                    } else {
                        activityBinding.root.context.showToast(response.errorMessage)
                    }
                }
                is ResponseResult.ERROR -> {
//                        errorMessage.postValue(result.result.errorMsg.toString())
                    activityBinding.root.context.showToast(result.result.errorMsg.toString())
                }
                is ResponseResult.FAILURE -> {
//                    binding.root.context.showToast(result.toString())
                }
            }
            activityBinding.loader.visibility = View.GONE

        }

    }


}