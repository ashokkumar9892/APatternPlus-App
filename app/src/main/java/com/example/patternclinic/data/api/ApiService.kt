package com.example.patternclinic.data.api

import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST(ApiConstants.LOGIN_API)
    suspend fun login(@Body map: HashMap<String, Any>): Response<LoginResponse>

    @POST(ApiConstants.FORGOT_PASSWORD)
    suspend fun forgotPassword(@Body map: HashMap<String, Any>): Response<BasicResponse>

    @POST(ApiConstants.RESET_PASSWORD)
    suspend fun resetPassword(@Body map: HashMap<String, Any>): Response<ResetPasswordResponse>

    @POST(ApiConstants.UPDATE_PROFILE_API)
    suspend fun updateProfile(@Body map: HashMap<String, Any>): Response<UpdateProfileResponse>

    @POST(ApiConstants.COACH_LIST_API)
    suspend fun getCoachList(@Body map: HashMap<String, Any>): Response<CoachProviderListResponse>

    @POST(ApiConstants.DOCTOR_LIST_API)
    suspend fun getDoctorList(@Body map: HashMap<String, Any>): Response<CoachProviderListResponse>

    @POST(ApiConstants.SELECT_AP_TEAM)
    suspend fun selectApTeam(@Body map: HashMap<String, Any>): Response<UpdateProfileResponse>

    @POST(ApiConstants.SIGN_UP)
    suspend fun signUp(@Body map: HashMap<String, Any>): Response<BasicResponse>

    @POST(ApiConstants.OTP)
    suspend fun sendOtp(@Body map: HashMap<String, Any>): Response<LoginResponse>

    @POST(ApiConstants.MY_CHAT_URL)
    suspend fun myChat(@Body map: HashMap<String, Any>): Response<MyChatResponse>

    @POST(ApiConstants.USER_CHAT_LIST)
    suspend fun getUserChatList(@Body map: HashMap<String, Any>): Response<GetUserChatResponse>

    @POST(ApiConstants.MANAGE_NOTIFICATION)
    suspend fun manageNotification(@Body map: HashMap<String, Any>): Response<BasicResponse>

    @POST(ApiConstants.CREATE_APPOINTMENT)
    suspend fun createAppointment(@Body map: HashMap<String, Any>): Response<BasicResponse>

    @POST(ApiConstants.APPOINTMENT_LIST)
    suspend fun appointmentList(@Body map: HashMap<String, Any>): Response<AppointmentListResponse>

    @POST(ApiConstants.CONTACT_US)
    suspend fun contactUs(@Body map: HashMap<String, Any>):Response<BasicResponse>

    @POST(ApiConstants.HEALTH_TIPS_LIST)
    suspend fun healthTips(@Body map: HashMap<String, Any>):Response<HealthTipsResponse>

    @Multipart
    @POST(ApiConstants.UPLOAD_FILE_URL)
    suspend fun uploadFiles(@Part file: MultipartBody.Part): Response<UploadFileResponse>


}