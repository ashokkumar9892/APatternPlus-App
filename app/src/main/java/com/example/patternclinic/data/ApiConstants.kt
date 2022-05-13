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
    const val SIGN_UP = "Cognito/CognitoUser"
    const val OTP = "Cognito/ConfirmOTP"
    const val MY_CHAT_URL = "Chat/MyChat"
    const val USER_CHAT_LIST = "Chat/ChatUsers"
    const val UPLOAD_FILE_URL = "Chat/UploadFiles"
    const val CHAT_HUB_URL = "https://patternclinicapis.harishparas.com/ChatHub"


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
        DEVICE_TOKEN("DeviceToken"),
        DEVICE_TYPE("DeviceType"),
        FORGOT_PASSWORD_USER_NAME("UserName"),
        RESET_PASSWORD_USER_NAME("username"),
        NEW_PASSWORD("newpassword"),
        CONFIRM_CODE("confirmationcode"),
        SK("SK"),

        HEIGHT("Height"),
        AUTH_TOKEN("AuthToken"),
        CONFIRMATION_CODE("confirmationcode"),
        WEIGHT("Weight"),
        FIRST_NAME("FirstName"),
        LAST_NAME("LastName"),
        EMAIL("Email"),
        COUNTRY("Country"),
        PROFILE_PIC("ProfilePic"),
        DOB("DOB"),
        GENDER("Gender"),
        REFER_AS("ReferAs"),
        WEIGHT_UNIT("weightUnit"),
        USER_NAME("UserName"),
        DOCTOR_ID("DoctorId"),
        DOCTOR_NAME("DoctorName"),
        COACH_NAME("CoachName"),
        COACH_ID("CoachId"),
        SENDER_SK("SenderSK"),
        RECEIVER_SK("ReceiverSK"),
        MESSAGE_TYPE("MessageType"),
        MESSAGE("Message"),
        FILE_NAME("files"),

    }

}