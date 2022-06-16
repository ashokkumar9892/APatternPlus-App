package com.example.patternclinic.home.drawerFragments.messages

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.MyChatResponse
import com.example.patternclinic.data.model.UploadFileResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.databinding.ActivityChatBinding
import com.example.patternclinic.home.drawerFragments.messages.zoomPackage.CodeChallengeHelper
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.OnClosedCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import us.zoom.sdk.*
import javax.inject.Inject

@HiltViewModel
class ChatActivityViewModel @Inject constructor(
    val mainRepository: MainRepository,

    ) :
    BaseViewModel() {
    var chatData = MutableLiveData<MyChatResponse>()
    var uploadFileResponse = MutableLiveData<UploadFileResponse>()
    var connection2: HubConnection? = null
    var connectStatus = true
    var binding: ActivityChatBinding? = null
    var codeChallengeHelper: CodeChallengeHelper? = null
    var sdk:ZoomSDK? = null

    init {
        makeConnection()

    }


    /**
     * Initialize the SDK with your credentials. This is required before accessing any of the
     * SDK's meeting-related functionality.
     */
    fun initializeSdk(context: Context) {
        sdk = ZoomSDK.getInstance()
        // TODO: Do not use hard-coded values for your key/secret in your app in production!
        val params = ZoomSDKInitParams().apply {
            appKey =
                "Eqp7eOoPCWOnb9OOtNgvqmJBnA8WJn7yqRk0" // TODO: Retrieve your SDK key and enter it here
            appSecret =
                "xgH0QMAHhPUfrXEhhju4A77X6OebcQEKA4DM" // TODO: Retrieve your SDK secret and enter it here
            domain = "zoom.us"
            enableLog = true // Optional: enable logging for debugging
        }
        // TODO (optional): Add functionality to this listener (e.g. logs for debugging)
        val listener = object : ZoomSDKInitializeListener {
            /**
             * If the [errorCode] is [ZoomError.ZOOM_ERROR_SUCCESS], the SDK was initialized and can
             * now be used to join/start a meeting.
             */
            override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) = Unit
            override fun onZoomAuthIdentityExpired() = Unit
        }
        sdk!!.initialize(context, listener, params)
    }

    /**
     * Join a meeting without any login/authentication with the meeting's number & password
     */
    fun joinMeeting(context: Context, meetingNumber: String, pw: String) {
        val meetingService = ZoomSDK.getInstance().meetingService
        val options = JoinMeetingOptions()
        val params = JoinMeetingParams().apply {
            displayName = "USER" // TODO: Enter your name
            meetingNo = meetingNumber
            password = pw


        }

        meetingService.joinMeetingWithParams(context, params, options)
    }

    fun startMeetingZak(context: Context) {
        val meetingService = ZoomSDK.getInstance().meetingService
        val options = StartMeetingOptions()
//         val par=StartMeetingParams4NormalUser().apply {
//             meetingNo="df"
//             this.vanityID="lkdjflks"
//
//         }
//         val para=StartMeetingParams().apply {
//             this.meetingNo="dsfsd"
//             this.vanityID="dsfl"
//         }
//
        val params = StartMeetingParamsWithoutLogin().apply {
            displayName = "name" // TODO: Enter your name
            userId = "id" // TODO: Enter userId
            zoomAccessToken =
                "eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiJiY2U4MDZjMi00NjViLTQxOTktYmRhYy1jMzdkM2NjNmVjZWQifQ.eyJ2ZXIiOjcsImF1aWQiOiJhZTNkYTQ1OTExZGRkN2YyMmQyNzkxYzYwNTkxODA4ZiIsImNvZGUiOiJrZlNBNzhjbzQ1X2NfZ0YwVS0yUktTaEVDZ3VodzJEeWciLCJpc3MiOiJ6bTpjaWQ6ZU9XbXFZTmhSc1Nnblo5Z1lidlJ3IiwiZ25vIjowLCJ0eXBlIjoxLCJ0aWQiOjAsImF1ZCI6Imh0dHBzOi8vb2F1dGguem9vbS51cyIsInVpZCI6ImNfZ0YwVS0yUktTaEVDZ3VodzJEeWciLCJuYmYiOjE2NTQ2NTkyNDcsImV4cCI6MjEyNzY5OTI0NywiaWF0IjoxNjU0NjU5MjQ3LCJhaWQiOiJ2M2hqUnZ2V1E4S0piMEkzaXl5M29RIiwianRpIjoiNTJmN2EzZjYtZGY1Mi00Yzk5LThmZDctNTE3NDIzZWU1ZTBlIn0.Am0WpW_Xy0nahSh4QBHCZOFsiD9jkks0N49TiCKjevQHLD6rmJ-FQQzyDrYSAmSXFaWNSLmOIlntAIAdnMR6xA" // TODO: Enter ZAK
        }
        meetingService.startMeetingWithParams(context, params, options)
    }

    /**
     * making connection to socket
     */
    fun makeConnection() {
        viewModelScope.launch {
            connection2 = HubConnectionBuilder.create(ApiConstants.CHAT_HUB_URL)
                .build()
            connection2!!.serverTimeout = 10000000
            connection2!!.keepAliveInterval = 10000000
            connection2!!.start()
//            connection2!!.onClosed(object : OnClosedCallback {
//                override fun invoke(exception: Exception?) {
//                    if (connectStatus) {
//                        connection2!!.start()
//                    }
//                }
//            })
            connection2!!.onClosed(object : OnClosedCallback {
                override fun invoke(exception: java.lang.Exception?) {
                    connection2!!.start()
                }
            })
        }
    }

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

    fun uploadFile(file: MultipartBody.Part) {
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

    override fun onCleared() {
        super.onCleared()
        connectStatus = false
        if (connection2 != null) {
            connection2!!.close()
        }
    }


}