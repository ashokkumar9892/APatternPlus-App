package com.example.patternclinic.data.model

data class MyChatResponse(
    val chatlist: List<Chatlist>,
    val errorMessage: String,
    val response: Int
)

data class Chatlist(
    val authToken: Any,
    val chatId: Any,
    val chatType: Any,
    val connectionId: Any,
    val fromIsRead: Any,
    val isAdmin: Boolean,
    val isNotification: Boolean,
    val message: String,
    val messageOn: Any,
    val receiverId: Any,
    val receiverImage: Any,
    val senderId: Any,
    val senderImage: Any,
    val sentOn: String,
    val toIsRead: Any,
    val totalBill: Any
)