package com.example.patternclinic.home.drawerFragments.appointments

import android.content.Context
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.api.ApiService
import com.example.patternclinic.utils.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import us.zoom.sdk.*
import javax.inject.Inject

@HiltViewModel
class CoachDetailViewModel @Inject constructor(val apiService: ApiService) : BaseViewModel() {
    var sdk: ZoomSDK? = null

    /**
     * Initialize the SDK with your credentials. This is required before accessing any of the
     * SDK's meeting-related functionality.
     */
    fun initializeSdk(context: Context):Boolean {
        sdk = ZoomSDK.getInstance()
        // TODO: Do not use hard-coded values for your key/secret in your app in production!
        val params = ZoomSDKInitParams().apply {
            appKey = "Eqp7eOoPCWOnb9OOtNgvqmJBnA8WJn7yqRk0" // TODO: Retrieve your SDK key and enter it here
            appSecret = "xgH0QMAHhPUfrXEhhju4A77X6OebcQEKA4DM" // TODO: Retrieve your SDK secret and enter it here
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
        return sdk?.isInitialized == true
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

    fun joinWithLink(context: Context, link: String) {

        initializeSdk(context)
        ZoomSDK.getInstance().meetingService.handZoomWebUrl(link)
    }
}