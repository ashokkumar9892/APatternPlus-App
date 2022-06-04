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
    const val MANAGE_NOTIFICATION = "Basic/ManageNotifications"
    const val CREATE_APPOINTMENT = "Basic/CreateAppointment"
    const val APPOINTMENT_LIST = "Basic/AppointmentList"
    const val ADD_STRIP_USER = "api/Stripe/AddStripeUsers"
    const val HEALTH_TIPS_LIST = "Basic/HealthTipsList"
    const val CONTACT_US = "Basic/ContactUs"
    const val CHAT_HUB_URL = "https://annexappapi.apatternplus.com/chatHub"
    const val SIGN_UP_URL = "https://www.apatternplus.com/a-p-plans-21651001758733"


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
        FULL_NAME("FullName"),
        SUBJECT("Subject"),
        CONFIRM_CODE("confirmationcode"),
        APPOINTMENT_STATUS("AppointmentStatus"),
        APPOINTMENT_DATE("AppointmentDate"),
        LOCATION("Location"),
        APPOINTMENT_TIME("AppointmentTime"),
        APPOINTMENT_TYPE("AppointmentType"),
        SK("SK"),
        IS_NOTIFICATION_ON("IsNotificationOn"),
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
        DOCTOR_SK("DoctorSK"),
        PATIENT_SK("PatientSK"),
        COACH_NAME("CoachName"),
        COACH_ID("CoachId"),
        SENDER_SK("SenderSK"),
        RECEIVER_SK("ReceiverSK"),
        MESSAGE_TYPE("MessageType"),
        MESSAGE("Message"),
        FILE_NAME("files"),

    }

}