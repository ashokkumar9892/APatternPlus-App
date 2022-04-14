package com.example.patternclinic.data.api

import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.LoginResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST(ApiConstants.LOGIN_API)
    suspend fun login(@Body map: HashMap<String, Any>): Response<LoginResponse>
}