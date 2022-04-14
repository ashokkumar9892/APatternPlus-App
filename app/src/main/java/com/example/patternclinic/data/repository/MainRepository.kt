package com.example.patternclinic.data.repository

import com.example.patternclinic.data.api.ApiHelper
import com.example.patternclinic.data.model.LoginResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.FieldMap
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun login( map: HashMap<String, Any>): Response<LoginResponse> =
        apiHelper.login(map)
}
