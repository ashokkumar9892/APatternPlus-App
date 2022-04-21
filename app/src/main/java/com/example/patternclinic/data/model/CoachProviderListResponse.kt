package com.example.patternclinic.data.model

data class CoachProviderListResponse(
    val authToken: Any,
    val doctorInfo: List<DoctorInfo>,
    val errorMessage: String,
    val response: Int
)

data class DoctorInfo(
    val designation: Any?,
    val profileImage: Any,
    val sk: String,
    val userName: String
)