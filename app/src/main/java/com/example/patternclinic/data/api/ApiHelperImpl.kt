package com.example.patternclinic.data.api

import com.example.patternclinic.data.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun login(map: HashMap<String, Any>): Response<LoginResponse> =
        apiService.login(map)

    override suspend fun forgotPassword(map: HashMap<String, Any>): Response<BasicResponse> =
        apiService.forgotPassword(map)

    override suspend fun resetPassword(map: HashMap<String, Any>): Response<ResetPasswordResponse> =
        apiService.resetPassword(map)

    override suspend fun updateProfile(map: HashMap<String, Any>): Response<UpdateProfileResponse> =
        apiService.updateProfile(map)

    override suspend fun getCoachList(map: HashMap<String, Any>): Response<CoachProviderListResponse> =
        apiService.getCoachList(map)

    override suspend fun getDoctorList(map: HashMap<String, Any>): Response<CoachProviderListResponse> =
        apiService.getDoctorList(map)

    override suspend fun selectApTeam(map: HashMap<String, Any>): Response<UpdateProfileResponse> =
        apiService.selectApTeam(map)

    override suspend fun signUp(map: HashMap<String, Any>): Response<BasicResponse> =
        apiService.signUp(map)

    override suspend fun sendOtp(map: HashMap<String, Any>): Response<LoginResponse> =
        apiService.sendOtp(map)

    override suspend fun myChat(map: HashMap<String, Any>): Response<MyChatResponse> =
        apiService.myChat(map)

    override suspend fun uploadFiles(file: MultipartBody.Part): Response<UploadFileResponse> =
        apiService.uploadFiles(file)

    override suspend fun getUserChatList(map: HashMap<String, Any>): Response<GetUserChatResponse> =
        apiService.getUserChatList(map)

    override suspend fun manageNotification(map: HashMap<String, Any>): Response<BasicResponse> =
        apiService.manageNotification(map)

    override suspend fun createAppointment(map: HashMap<String, Any>): Response<BasicResponse> =
        apiService.createAppointment(map)

    override suspend fun appointmentList(@Body map: HashMap<String, Any>): Response<AppointmentListResponse> =
        apiService.appointmentList(map)

}