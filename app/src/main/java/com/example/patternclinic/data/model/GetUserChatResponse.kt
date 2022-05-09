package com.example.patternclinic.data.model


data class GetUserChatResponse(
    val chatId: Int,
    val count: Int?,
    val image: Any,
    val lastMessage: String,
    val lastMessageType: Any,
    val name: String?,
    val recieverSK: String,
    val senderSK: String,
    val timeBefore: Any,
    val uniqueNumber: Any,
    val unseenCount: Int?
)