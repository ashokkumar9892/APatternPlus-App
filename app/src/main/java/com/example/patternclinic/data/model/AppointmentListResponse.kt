package com.example.patternclinic.data.model

data class AppointmentListResponse(
    val appointmentInfo: List<AppointmentInfo>,
    val authToken: Any,
    val currentteam: Currentteam,
    val errorMessage: String,
    val response: Int
)

data class AppointmentInfo(


    val appointmentDate: String,
    val appointmentStatus: String,
    val appointmentTime: String,
    val appointmentType: String?,
    val authToken: Any,
    val createdOn: Any,
    val doctorName: String,
    val doctorPic: String?,
    val doctorSK: String,
    val location: Any,
    val patientSK: String,
    val pk: Any,
    val sk: Any

)

data class Currentteam(
    val coachName: String?,
    val coachPic: Any,
    val coachSK: String,
    val doctorName: String,
    val doctorPic: Any,
    val doctorSK: String,
    val teamLocation: String?
)