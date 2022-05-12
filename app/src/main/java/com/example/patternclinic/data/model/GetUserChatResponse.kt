package com.example.patternclinic.data.model


data class GetUserChatResponse(
    val chatlist: List<ChatInfo>,
    val errorMessage: String,
    val response: Int
)

data class ChatInfo(
    val authToken: Any,
    val chatId: Any,
    val chatType: Any,
    val connectionId: Any,
    val fromIsRead: Any,
    val isAdmin: Boolean,
    val unseenCount: Int?,
    val isNotification: Boolean,
    val message: String?,
    val messageOn: Any,
    val name: String?,
    val profilePic: String,
    val receiverId: Any,
    val receiverImage: Any,
    val senderId: Any,
    val senderImage: Any,
    val lastMessage: Any?,
    val sentOn: Any,
    val sk: String,
    val toIsRead: Any,
    val totalBill: Any
)