package com.example.patternclinic.home.drawerFragments.myProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.model.UpdateProfileResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(val mainRepository: MainRepository) : BaseViewModel() {
    var updateProfileResponse = MutableLiveData<UpdateProfileResponse>()

    fun createProfileApi(map: HashMap<String, Any>) {

        showLoader.value = true
        job = viewModelScope.launch {
            val result = getResult({ mainRepository.updateProfile(map) }, "updateProfile")

            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as UpdateProfileResponse)
                    updateProfileResponse.postValue(response)
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