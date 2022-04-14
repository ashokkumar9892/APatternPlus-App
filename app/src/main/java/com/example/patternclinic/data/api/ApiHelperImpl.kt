package com.example.patternclinic.data.api

import com.example.patternclinic.data.api.ApiHelper
import com.example.patternclinic.data.api.ApiService
import com.example.patternclinic.data.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun login(map: HashMap<String, Any>): Response<LoginResponse> = apiService.login(map)


}