package com.example.patternclinic.data

object ApiConstants {
    //API END POINTS
    const val LOGIN_API = "Basic/Login"
    const val FORGOT_PASSWORD = "Basic/ForgotPassword"
    const val RESET_PASSWORD = "Basic/ResetPassword"
    const val UPDATE_PROFILE_API = "Basic/UpdateProfile"
    const val COACH_LIST_API = "Basic/CoachList"
    const val DOCTOR_LIST_API = "Basic/DoctorsList"
    const val SELECT_AP_TEAM = "Basic/SelectAPTeam"


    const val CONNECT_TIMEOUT = 240L //seconds
    const val WRITE_TIMEOUT = 240L//seconds
    const val READ_TIMEOUT = 240L//seconds

    enum class SubTypes(val value: String) {

    }

    enum class APITypes(val value: String) {

    }

    enum class APIParams(val value: String) {
        USERNAME("Username"),
        PASSWORD("Password"),
        FORGOT_PASSWORD_USER_NAME("UserName"),
        RESET_PASSWORD_USER_NAME("username"),
        NEW_PASSWORD("newpassword"),
        CONFIRM_CODE("confirmationcode"),
        SK("SK"),
        HEIGHT("Height"),
        AUTH_TOKEN("AuthToken"),
        WEIGHT("Weight"),
        FIRST_NAME("FirstName"),
        LAST_NAME("LastName"),
        EMAIL("Email"),
        COUNTRY("Country"),
        PROFILE_PIC("ProfilePic"),
        DOB("DOB"),
        GENDER("Gender"),
        REFER_AS("ReferAs"),
        USER_NAME("UserName"),
        DOCTOR_ID("DoctorId"),
        DOCTOR_NAME("DoctorName"),
        COACH_NAME("CoachName"),
        COACH_ID("CoachId"),


    }

}