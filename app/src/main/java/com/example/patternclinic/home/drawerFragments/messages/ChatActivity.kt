package com.example.patternclinic.home.drawerFragments.messages

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.databinding.ActivityChatBinding
import com.example.patternclinic.utils.showToast
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class ChatActivity : AppCompatActivity()
//    HubConnectionListener, HubEventListener
{
    lateinit var binding: ActivityChatBinding
//    lateinit var connection: WebSocketHubConnectionP2
    var chatList = mutableListOf<String>()
    lateinit var chatAdapter: ChatAdapter
    var connection2: HubConnection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        binding.ivBack.setOnClickListener {
            finish()
        }
        clicks()
        chatAdapter = ChatAdapter(chatList)
        binding.rvMessages.adapter = chatAdapter

        var url = "https://patternclinicapis.harishparas.com/ChatHub"
        /**
         * different connection
         */
//        connection2 = HubConnectionBuilder.create(url).withAccessTokenProvider()

        connection2 = HubConnectionBuilder.create(url)
            /*.withAccessTokenProvider(Single.defer {
                Single.just(
                    "Bearer eyJraWQiOiI3UGloR0p3MFlcL1dxdDRuSnMxQ0x0R2syOUZGYTZiSEhZVXpwNUY3N3Zlaz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZjIxNDFkMi0wMGU0LTQ4ZDItODZlZi1hZGEwOGE3YmNlOWMiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy13ZXN0LTIuYW1hem9uYXdzLmNvbVwvdXMtd2VzdC0yXzVBMUI4dExjOSIsImNvZ25pdG86dXNlcm5hbWUiOiJhcHBzZGV2ZWxvcGVyMjJAZ21haWwuY29tIiwib3JpZ2luX2p0aSI6ImZmOWUxYzY2LWRjNzQtNDU1MS05ZjhjLThiNDI0MjVkMTA2YiIsImF1ZCI6IjJncTBkN2k3YTZydWwwcTBhaTdsN2RzaWRzIiwiZXZlbnRfaWQiOiJhN2I1YjkwNi1iNWNiLTQwYTgtOGM1MS0yOTE0YWQ5YWQ3YmIiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTY1MTczMjQwOSwiZXhwIjoxNjUxNzM2MDA5LCJpYXQiOjE2NTE3MzI0MDksImp0aSI6IjYxMDZlMTdlLWIwZGYtNDJjZi04ZTYwLWU2YzBhMDBiNDQ0ZSIsImVtYWlsIjoiYXBwc2RldmVsb3BlcjEyM0BnbWFpbC5jb20ifQ.Rq3lojNSEt00twQUTMrCUcC9kmCaliZvVw5Carz5GTfguEBBTnwHgu7l3kH3NKjdv9vsrO5ChRLSsHQy77XbCgpfxAj8pDDJDlZyH6XNqxsHfkLXr91fN7MniKPhkvzYVbQM4y0N-0Rag7FNj46XXNjzd_PjPjZcwtT-R7wJinTIImcU-BTZWaXQCfyEFc--zGtlGls5oDnsPUBXGR1Uo3O49jPDFxpACoIjZVAZctQGrxaD-66ETaC-W71vaXPLkbwPR_MVOnONZ-H33h0fP_Wx3H8yOzF5nBcPUkWuXYjEqr3HyGJzG82BUj7XX4wOZAz-pqf-Ut5yrgZFO4Yd8Q"
                )
            })*/.build()
        connection2!!.start()

        connection2!!.on("ReceiveMessage", object : Action1<responsemodel> {
            override fun invoke(param1: responsemodel?) {

                    Log.d("asdasdasd",param1.toString())

            }

        }, responsemodel::class.java)



        /**
         * another connection
         */
//        connection = WebSocketHubConnectionP2(
//            url, ""
//        )
//        connection.addListener(this)
//        connection.subscribeToEvent(
//            "ReceiveMessage", this
//        )
//        connection.connect()
    }

    private fun clicks() {
        binding.ivSend.setOnClickListener {
            if (!binding.etMessage.text.toString().trim().isNullOrEmpty()) {
                var data=model("PATIENT_1650619112058","DOCTOR_120527155321379","Text","hello here","eyJraWQiOiI3UGloR0p3MFlcL1dxdDRuSnMxQ0x0R2syOUZGYTZiSEhZVXpwNUY3N3Zlaz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZjIxNDFkMi0wMGU0LTQ4ZDItODZlZi1hZGEwOGE3YmNlOWMiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy13ZXN0LTIuYW1hem9uYXdzLmNvbVwvdXMtd2VzdC0yXzVBMUI4dExjOSIsImNvZ25pdG86dXNlcm5hbWUiOiJhcHBzZGV2ZWxvcGVyMjJAZ21haWwuY29tIiwib3JpZ2luX2p0aSI6ImNlOTM4NmZhLWViOGQtNGNmNi1hNzBmLTViZGI2YjY5MzJmZiIsImF1ZCI6IjJncTBkN2k3YTZydWwwcTBhaTdsN2RzaWRzIiwiZXZlbnRfaWQiOiI4ZDYwN2M2OC00N2NhLTRkNTItYmIwOC1lYzQ3ZDI4NTI3NzAiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTY1MTc0MDgyNSwiZXhwIjoxNjUxNzQ0NDI1LCJpYXQiOjE2NTE3NDA4MjUsImp0aSI6IjkxMzkzY2RkLThhNDgtNGQxOC1hNjBmLTY3YWNhZTEyMmZkYiIsImVtYWlsIjoiYXBwc2RldmVsb3BlcjEyM0BnbWFpbC5jb20ifQ.YSfi_TIB0NomTICzK2_OP7usM3jwXkTJIoizT2Zm26NDWk7RYh8YGyomJFzBrv1SpRRvQusPH9a0YSrExsq-tQrQfL1LWJoctBV-mZRAiTGSnXClqMnY0_ob1YnTq-Ye6NMeACdQvhwJ_NHU0m2bRd__BJkCmIkq97gKS_EL92D-G_NbNyci_wxtnbVrcxRLmZTrxMI-AAkp4MUbyNxxDQL9E1YEJBqCw_h94utiqJ5P92oK_fDJ28As0s9L48d1HOTc-7LgSVAqgwVZkl3-CrIM4o6y_dbqu0OG5ux001Q93Pv8vEGBG8AnUgm9biB0AMvrw-zY7Y0b3RdYPJ3HNQ")
                var json = JSONObject()
                json.put(ApiConstants.APIParams.SENDER_SK.value, "PATIENT_1650619112058")
                json.put(ApiConstants.APIParams.RECEIVER_SK.value, "DOCTOR_120527155321379")
                json.put(ApiConstants.APIParams.MESSAGE_TYPE.value, "Text")
                json.put(ApiConstants.APIParams.MESSAGE.value, "hello here")
                json.put(ApiConstants.APIParams.AUTH_TOKEN.value,"eyJraWQiOiI3UGloR0p3MFlcL1dxdDRuSnMxQ0x0R2syOUZGYTZiSEhZVXpwNUY3N3Zlaz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZjIxNDFkMi0wMGU0LTQ4ZDItODZlZi1hZGEwOGE3YmNlOWMiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy13ZXN0LTIuYW1hem9uYXdzLmNvbVwvdXMtd2VzdC0yXzVBMUI4dExjOSIsImNvZ25pdG86dXNlcm5hbWUiOiJhcHBzZGV2ZWxvcGVyMjJAZ21haWwuY29tIiwib3JpZ2luX2p0aSI6ImNlOTM4NmZhLWViOGQtNGNmNi1hNzBmLTViZGI2YjY5MzJmZiIsImF1ZCI6IjJncTBkN2k3YTZydWwwcTBhaTdsN2RzaWRzIiwiZXZlbnRfaWQiOiI4ZDYwN2M2OC00N2NhLTRkNTItYmIwOC1lYzQ3ZDI4NTI3NzAiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTY1MTc0MDgyNSwiZXhwIjoxNjUxNzQ0NDI1LCJpYXQiOjE2NTE3NDA4MjUsImp0aSI6IjkxMzkzY2RkLThhNDgtNGQxOC1hNjBmLTY3YWNhZTEyMmZkYiIsImVtYWlsIjoiYXBwc2RldmVsb3BlcjEyM0BnbWFpbC5jb20ifQ.YSfi_TIB0NomTICzK2_OP7usM3jwXkTJIoizT2Zm26NDWk7RYh8YGyomJFzBrv1SpRRvQusPH9a0YSrExsq-tQrQfL1LWJoctBV-mZRAiTGSnXClqMnY0_ob1YnTq-Ye6NMeACdQvhwJ_NHU0m2bRd__BJkCmIkq97gKS_EL92D-G_NbNyci_wxtnbVrcxRLmZTrxMI-AAkp4MUbyNxxDQL9E1YEJBqCw_h94utiqJ5P92oK_fDJ28As0s9L48d1HOTc-7LgSVAqgwVZkl3-CrIM4o6y_dbqu0OG5ux001Q93Pv8vEGBG8AnUgm9biB0AMvrw-zY7Y0b3RdYPJ3HNQ")
                try {
                    //invoke from one connection
                        Handler(Looper.getMainLooper()).post(Runnable{
//                            connection.invoke("SendMessages",json)
                            //invoke from second connection

//                            connection2!!.invoke(Any::class.java,"SendMessages",json)

                        })

                    runOnUiThread {
                        connection2!!.invoke("SendMessages", data)
                    /* var a=   connection2!!.invoke(Any::class.java,"SendMessages", json)
                        Log.e("log",a.toString())*/
                    }
//
//                    showToast(connection2!!.connectionState.toString())
////                    connection.invoke("SendMessages", json)
                    chatList.add(binding.etMessage.text.toString().trim())
                    chatAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    showToast(e.toString())
                }
            }

        }
    }

    override fun onPause() {
        super.onPause()
//        connection.removeListener(this)
//        connection.unSubscribeFromEvent("SendMessages", this)
//        connection.disconnect()

    }
//
//    override fun onConnected() {
//        runOnUiThread {
//            showToast("connected")
//        }
//
//
//    }
//
//    override fun onDisconnected() {
//        runOnUiThread {
//            showToast("disconnected")
//        }
//    }
//
//    override fun onMessage(message: HubMessage?) {
//        runOnUiThread {
//            showToast("message")
//        }
//
//    }
//
//    override fun onError(exception: Exception?) {
//        runOnUiThread {
//            showToast(exception.toString())
//        }
//    }
//
//    override fun onEventMessage(message: HubMessage?) {
//        showToast(message.toString())
//    }

    data class model(var SenderSK:String,var ReceiverSK:String,var MessageType:String,var Message:String,var AuthToken:String)
    data class responsemodel(var image:String,var message:String,var messageType:String,var senderSK:String,var senton:String)
}