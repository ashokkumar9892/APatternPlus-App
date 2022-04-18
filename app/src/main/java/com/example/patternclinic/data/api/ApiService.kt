package com.example.patternclinic.data.api

import com.example.patternclinic.auth.ForgotPassword
import com.example.patternclinic.auth.ResetPassword
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.ForgotPasswordResponse
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.model.ResetPasswordResponse
import com.example.patternclinic.data.model.UpdateProfileResponse
import com.google.android.exoplayer2.text.span.TextAnnotation
import okhttp3.RequestBody
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



}