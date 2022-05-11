package com.example.patternclinic.home.drawerFragments.messages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.model.GetUserChatResponse
import com.example.patternclinic.data.model.MyChatResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageFragmentViewModel @Inject constructor(val mainRepository: MainRepository) :
    BaseViewModel() {
        var usersChat=MutableLiveData<GetUserChatResponse>()

    /**
     * below method used for get usersChatList
     */


    fun getUserChatList(map: HashMap<String, Any>) {
        showLoader.value = true
        job = viewModelScope.launch {
            val result = getResult({ mainRepository.getUserChatList(map) }, "getUserChatList")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as GetUserChatResponse)

                    usersChat.postValue(response)
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