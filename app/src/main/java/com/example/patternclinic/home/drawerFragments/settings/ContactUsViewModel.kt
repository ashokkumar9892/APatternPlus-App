package com.example.patternclinic.home.drawerFragments.settings

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.api.ApiService
import com.example.patternclinic.data.model.BasicResponse
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.model.MyChatResponse
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(val apiService: ApiService) : BaseViewModel() {
    val contactUsResponse = MutableLiveData<BasicResponse>()
    val name = ObservableField<String>()
    val email = ObservableField<String>()
    val subject = ObservableField<String>()
    val message = ObservableField<String>()
    var userDetail: LoginResponse? = null

    init {
        userDetail = SharedPrefs.getLoggedInUser()
        name.set(userDetail!!.patientInfo.firstName)
        email.set(userDetail!!.patientInfo.email)
    }

    /**
     * below method used for contact
     */



    fun contactUs(map: HashMap<String, Any>) {
        showLoader.value = true
        job = viewModelScope.launch {
            val result = getResult({ apiService.contactUs(map) }, "contactUs")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as BasicResponse)

                    contactUsResponse.postValue(response)
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
     * click events
     */
    fun click(v: View) {
        when (v.id) {
            R.id.tv_submit -> {
                if (validation(v.context)) {
                    val map = HashMap<String, Any>()
                    map[ApiConstants.APIParams.FULL_NAME.value] = name.get()!!.trim()
                    map[ApiConstants.APIParams.EMAIL.value] = email.get()!!.trim()
                    map[ApiConstants.APIParams.SUBJECT.value] = subject.get()!!.trim()
                    map[ApiConstants.APIParams.MESSAGE.value] = message.get()!!.trim()
                    map[ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail!!.authToken
                    contactUs(map)
                }
            }
        }
    }

    /**
     * validations for checking
     */
    private fun validation(context: Context): Boolean {
        if (subject.get()?.trim().isNullOrEmpty()) {
            context.showToast(context.getString(R.string.enter_your_subject))
            return false
        } else if (message.get()?.trim().isNullOrEmpty()) {
            context.showToast(context.getString(R.string.type_your_message))
            return false
        }
        return true
    }
}