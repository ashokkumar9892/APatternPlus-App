package com.example.patternclinic.data.repository

import com.example.patternclinic.data.api.ApiHelper
import com.example.patternclinic.data.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Part
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun login(map: HashMap<String, Any>): Response<LoginResponse> = apiHelper.login(map)

    suspend fun forgotPassword(@Body map: HashMap<String, Any>): Response<BasicResponse> = apiHelper.forgotPassword(map)
    suspend fun resetPassword(@Body map: HashMap<String, Any>): Response<ResetPasswordResponse> = apiHelper.resetPassword(map)
    suspend fun updateProfile(@Body map: HashMap<String, Any>):Response<UpdateProfileResponse> =  apiHelper.updateProfile(map)
    suspend fun getCoachList(@Body map: HashMap<String, Any>): Response<CoachProviderListResponse> = apiHelper.getCoachList(map)
    suspend fun getDoctorList(@Body map: HashMap<String, Any>): Response<CoachProviderListResponse> = apiHelper.getDoctorList(map)
    suspend fun selectApTeam(@Body map: HashMap<String, Any>): Response<UpdateProfileResponse> = apiHelper.selectApTeam(map)
    suspend fun signUp(@Body map: HashMap<String, Any>):Response<BasicResponse> = apiHelper.signUp(map)
    suspend fun sendOtp(@Body map: HashMap<String, Any>):Response<LoginResponse> = apiHelper.sendOtp(map)
    suspend fun myChat(@Body map: HashMap<String, Any>): Response<MyChatResponse> = apiHelper.myChat(map)
    suspend fun uploadFiles(@Part file: MultipartBody.Part): Response<UploadFileResponse> = apiHelper.uploadFiles(file)
    suspend fun getUserChatList(@Body map: HashMap<String, Any>): Response<GetUserChatResponse> = apiHelper.getUserChatList(map)
    suspend fun manageNotification(@Body map: HashMap<String, Any>): Response<BasicResponse> = apiHelper.manageNotification(map)
    suspend fun createAppointment(@Body map: HashMap<String, Any>): Response<BasicResponse> = apiHelper.createAppointment(map)
}
