package com.example.patternclinic.home.drawerFragments.messages

import android.media.AudioManager
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.model.MyChatResponse
import com.example.patternclinic.data.model.UploadFileResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class ChatActivityViewModel @Inject constructor(val mainRepository: MainRepository):BaseViewModel() {
    var chatData= MutableLiveData<MyChatResponse>()
    var uploadFileResponse= MutableLiveData<UploadFileResponse>()

    /**
     * below method used for get chat
     */


    fun getChat(map: HashMap<String, Any>) {
        showLoader.value = true
        job = viewModelScope.launch {
            val result = getResult({ mainRepository.myChat(map) }, "getChat")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as MyChatResponse)

                    chatData.postValue(response)
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
     * below method used for get upload_files
     */

    fun uploadFile(file:MultipartBody.Part) {
        showLoader.value = true
        job = viewModelScope.launch {
            val result = getResult({ mainRepository.uploadFiles(file) }, "uploadFile")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as UploadFileResponse)

                    uploadFileResponse.postValue(response)
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