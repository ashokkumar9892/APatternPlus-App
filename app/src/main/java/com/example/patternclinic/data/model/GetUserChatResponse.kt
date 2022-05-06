package com.example.patternclinic.data.model

//data class GetUserChatResponse(
//    var chatId:Int?,
//    var count:Int?,
//    var lastMessage:String?,
//    var name:String?,
//    var recieverSK:String?,
//    var senderSK:String?,
//    var unseencount:Int?
//)
//data class GetUserChatResponse(
//    val arguments: List<Argument>,
//    val target: String,
//    val type: Int
//)
//data class GetUserChatResponse(
//    val arguments: List<Argument>,
//    val target: String,
//    val type: Int
//)

data class Argument(
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