package com.example.patternclinic.home.drawerFragments.appointments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.api.ApiService
import com.example.patternclinic.data.model.AppointmentListResponse
import com.example.patternclinic.databinding.ActivityCoachAppointmentsBinding
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import com.example.patternclinic.utils.Keys
import com.example.patternclinic.utils.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentListViewModel @Inject constructor(val apiService: ApiService) : BaseViewModel() {
    val binding: ActivityCoachAppointmentsBinding? = null
    val appointmentListResponse = MutableLiveData<AppointmentListResponse>()
    val userDetail = SharedPrefs.getLoggedInUser()

    init {
        upcomingAppointments()
    }

    private fun upcomingAppointments() {
        val map = HashMap<String, Any>()
        map[ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail!!.authToken
        map[ApiConstants.APIParams.APPOINTMENT_STATUS.value] = Keys.APPOINTMENT_STATUS_SENT
        map[ApiConstants.APIParams.PATIENT_SK.value] = userDetail.patientInfo.sk
        appointmentList(map)
    }

    /**
     * below method used for get appointment
     */

    fun appointmentList(map: HashMap<String, Any>) {
        showLoader.value = true
        job = viewModelScope.launch {
            val result = getResult({ apiService.appointmentList(map) }, "appointmentList")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as AppointmentListResponse)

                    appointmentListResponse.postValue(response)
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