package com.example.patternclinic.data.model


data class LoginResponse(
    val authToken: String,
    val errorMessage: String,
    val patientInfo: PatientInfo,
    val response: Int
)

data class PatientInfo(
    val country: Any,
    val dob: String?,
    val email: String,
    val firstName: String?,
    val gender: String,
    val height: String,
    val lastName: String,
    val profilePic: String,
    val weightUnit: String,
    val referAs: Any,
    val sk: String,
    val userName: String,
    val weight: Any
)