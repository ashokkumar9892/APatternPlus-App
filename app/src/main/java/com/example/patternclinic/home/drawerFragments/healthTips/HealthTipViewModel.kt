package com.example.patternclinic.home.drawerFragments.healthTips

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.api.ApiService
import com.example.patternclinic.data.model.HealthTipsResponse
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.model.MyChatResponse
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import com.example.patternclinic.utils.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthTipViewModel @Inject constructor(val apiService: ApiService) : BaseViewModel() {
    val healthTipResponse = MutableLiveData<HealthTipsResponse>()
    val adapter = HealthTipsAdapter()
    var userDetail: LoginResponse? = null

    /**
     * below method used for get chat
     */
    init {
        userDetail = SharedPrefs.getLoggedInUser()
        val map = HashMap<String, Any>()
        map[ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail!!.authToken
        getHealthTips(map)
    }


    fun getHealthTips(map: HashMap<String, Any>) {
        showLoader.value = true
        job = viewModelScope.launch {
            val result = getResult({ apiService.healthTips(map) }, "healthTips")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as HealthTipsResponse)

                    healthTipResponse.postValue(response)
                    if (response.response == 1) {
                        adapter.addList(response.tips)
                    }
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