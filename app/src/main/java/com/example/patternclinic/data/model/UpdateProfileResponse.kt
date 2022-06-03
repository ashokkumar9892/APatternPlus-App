package com.example.patternclinic.data.model

data class UpdateProfileResponse(
    val authToken: String,
    val errorMessage: String,
    val patientInfo: PatientInfo?,
    val response: Int
)