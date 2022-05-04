package com.example.patternclinic.data.api

import com.example.patternclinic.auth.ForgotPassword
import com.example.patternclinic.auth.ResetPassword
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.*
import com.google.android.exoplayer2.metadata.id3.ApicFrame
import com.google.android.exoplayer2.text.span.TextAnnotation
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST(ApiConstants.LOGIN_API)
    suspend fun login(@Body map: HashMap<String, Any>): Response<LoginResponse>

    @POST(ApiConstants.FORGOT_PASSWORD)
    suspend fun forgotPassword(@Body map: HashMap<String, Any>):Response<ForgotPasswordResponse>

    @POST(ApiConstants.RESET_PASSWORD)
    suspend fun resetPassword(@Body map: HashMap<String, Any>): Response<ResetPasswordResponse>

    @POST(ApiConstants.UPDATE_PROFILE_API)
    suspend fun updateProfile(@Body map: HashMap<String, Any>):Response<UpdateProfileResponse>

    @POST(ApiConstants.COACH_LIST_API)
    suspend fun getCoachList(@Body map: HashMap<String, Any>):Response<CoachProviderListResponse>

    @POST(ApiConstants.DOCTOR_LIST_API)
    suspend fun getDoctorList(@Body map: HashMap<String, Any>): Response<CoachProviderListResponse>

    @POST(ApiConstants.SELECT_AP_TEAM)
    suspend fun selectApTeam(@Body map: HashMap<String, Any>): Response<UpdateProfileResponse>

    @POST(ApiConstants.SIGN_UP)
    suspend fun signUp(@Body map: HashMap<String, Any>):Response<ForgotPasswordResponse>

    @POST(ApiConstants.OTP)
    suspend fun sendOtp(@Body map: HashMap<String, Any>):Response<LoginResponse>





}