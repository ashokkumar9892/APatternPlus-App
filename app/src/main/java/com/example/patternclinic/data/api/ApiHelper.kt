package com.example.patternclinic.data.api


import com.example.patternclinic.auth.ForgotPassword
import com.example.patternclinic.auth.ResetPassword
import com.example.patternclinic.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.Part

interface ApiHelper {

    suspend fun login(@Body map: HashMap<String, Any>):Response<LoginResponse>

    suspend fun forgotPassword(@Body map: HashMap<String, Any>):Response<ForgotPasswordResponse>

    suspend fun resetPassword(@Body map: HashMap<String, Any>): Response<ResetPasswordResponse>

    suspend fun updateProfile(@Body map: HashMap<String, Any>):Response<UpdateProfileResponse>
    suspend fun getCoachList(@Body map: HashMap<String, Any>):Response<CoachProviderListResponse>
    suspend fun getDoctorList(@Body map: HashMap<String, Any>): Response<CoachProviderListResponse>
    suspend fun selectApTeam(@Body map: HashMap<String, Any>): Response<UpdateProfileResponse>
    suspend fun signUp(@Body map: HashMap<String, Any>):Response<ForgotPasswordResponse>
    suspend fun sendOtp(@Body map: HashMap<String, Any>):Response<LoginResponse>
    suspend fun myChat(@Body map: HashMap<String, Any>): Response<MyChatResponse>
    suspend fun uploadFiles(@Part file: MultipartBody.Part): Response<UploadFileResponse>
    suspend fun getUserChatList(@Body map: HashMap<String, Any>): Response<GetUserChatResponse>
    suspend fun manageNotification(@Body map: HashMap<String, Any>): Response<ForgotPasswordResponse>

}