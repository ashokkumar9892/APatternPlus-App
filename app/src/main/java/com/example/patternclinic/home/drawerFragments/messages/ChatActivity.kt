package com.example.patternclinic.home.drawerFragments.messages

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devlomi.record_view.OnRecordListener
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.api.RequestBodyRetrofit
import com.example.patternclinic.data.model.ChatInfo
import com.example.patternclinic.data.model.Chatlist
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.ActivityChatBinding
import com.example.patternclinic.utils.*
import com.google.gson.Gson
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.vincent.videocompressor.VideoCompress
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.jar.Manifest


@AndroidEntryPoint
class ChatActivity : BaseActivity() {
    lateinit var binding: ActivityChatBinding

    lateinit var chatAdapter: ChatAdapter
//    var connection2: HubConnection? = null
    val viewModel: ChatActivityViewModel by viewModels()
    var recorder: MediaRecorder? = null
    var map: HashMap<String, Any>? = null
    var userDetail: LoginResponse? = null
    var receiverUser: ChatInfo? = null
    var receiverSk: String? = null
    var notificationData: MutableMap<String, Any>? = null
    var currentFile: File? = null

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        userDetail = SharedPrefs.getLoggedInUser()

        if (intent.hasExtra(Keys.NOTIFICATIONS)) {

            notificationData =
                Gson().fromJson(
                    intent.getStringExtra(Keys.NOTIFICATIONS),
                    MutableMap::class.java
                ) as MutableMap<String, Any>
            receiverSk = notificationData!!.get("messagesentby").toString()
        } else {
            receiverUser = Gson().fromJson(intent.getStringExtra(Keys.CHAT), ChatInfo::class.java)
            receiverSk = receiverUser!!.sk
            binding.tvTitle.text = receiverUser!!.name ?: ""
            if (receiverUser!!.chatStatus == Keys.STATUS_INACTIVE) {
                binding.rlSendingFunctionalityContainer.visibility = View.GONE
            }
        }


        clicks()
        setObservers()
        /**
         * fetching chat data
         */
        map = HashMap()
        map!![ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail!!.authToken
        map!![ApiConstants.APIParams.SENDER_SK.value] = userDetail!!.patientInfo.sk
        map!![ApiConstants.APIParams.RECEIVER_SK.value] = receiverSk!!.uppercase()
        viewModel.getChat(map!!)

//        connection2 = HubConnectionBuilder.create(ApiConstants.CHAT_HUB_URL)
//            .build()
//        connection2!!.start()
//        connection2!!.serverTimeout=10000000
//        connection2!!.onClosed {
//            connection2!!.start()
//        }


       viewModel.connection2!!.on("ReceiveMessage", object : Action1<responsemodel> {
            override fun invoke(param1: responsemodel?) {

                if (param1!!.message.isNullOrEmpty()) {
                    runOnUiThread {
                        showToast(param1.messageType)
                    }

                } else {
                    runOnUiThread {
                        binding.loader.visibility = View.GONE
                        chatAdapter.addMessage(
                            Chatlist(
                                userDetail!!.authToken,
                                "",
                                param1.messageType,
                                "",
                                "",
                                false,
                                false,
                                param1.message!!,
                                "",
                                "",
                                "",
                                param1.senderSK,
                                "",
                                param1.senton,
                                "",
                                ""
                            )
                        )

                        (binding.rvMessages.layoutManager as LinearLayoutManager).scrollToPosition(
                            chatAdapter.list.size - 1
                        )
//                        binding.etMessage.setText("")
                    }
                }
            }

        }, responsemodel::class.java)


    }

    fun setObservers() {
        /**
         * observer for sign up response
         */

        viewModel.chatData.observe(this) {
            if (it.response == 1) {

                var a = it.chatlist.reversed()
                chatAdapter = ChatAdapter(a.toMutableList())
                (binding.rvMessages.layoutManager as LinearLayoutManager).stackFromEnd = true
                binding.rvMessages.adapter = chatAdapter
            } else {
                showToast(it.errorMessage)
            }
        }

        /**
         * observer for loader
         */
        viewModel.showLoader.observe(this)
        {
            if (it) {
                binding.loader.visibility = View.VISIBLE
            } else {
                binding.loader.visibility = View.GONE
            }
        }
        /**
         * observer for error-response
         */
        viewModel.errorMessage.observe(this)
        {
            showToast(it)
        }
        /**
         * observer for failure-response
         */
        viewModel.onFailure.observe(this)
        {
            showToast(it.toString())
        }

        /**
         * observer for upload_file response
         */
        viewModel.uploadFileResponse.observe(this) {
            if (it.response == 1) {
                val data = model(
                    userDetail!!.patientInfo.sk,
                    receiverSk!!,
                    it.imageurls[0].filetype,
                    it.imageurls[0].files,
                    SharedPrefs.getLoggedInUser()!!.authToken,


                    )

                try {
                    //invoke from one connection
                    runOnUiThread {
                        viewModel.connection2!!.invoke("SendMessages", data)
                        binding.etMessage.setText("")
                        binding.loader.visibility = View.VISIBLE
//
                    }

                } catch (e: Exception) {
                    showToast(e.toString())
                }
            } else {
                showToast(it.errorMessage)
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun clicks() {
        binding.ivSend.setOnClickListener {
            if (binding.etMessage.text.toString().trim().isNotEmpty()) {
                val data = model(
                    userDetail!!.patientInfo.sk,
                    receiverSk!!,
                    "Text",
                    binding.etMessage.text.trim().toString(),
                    SharedPrefs.getLoggedInUser()!!.authToken,
                )

                try {
                    //invoke from one connection
                    runOnUiThread {
                        viewModel.connection2!!.invoke("SendMessages", data)
                        binding.etMessage.setText("")
                        binding.loader.visibility = View.VISIBLE
//
                    }

                } catch (e: Exception) {
                    showToast(e.toString())
                }
            }

        }

        binding.ivBack.setOnClickListener {

            finish()
        }
        binding.ivCamera.setOnClickListener {
            grantPermission(PermissionConstant.cameraGalleryPermissionList) {
                imageVideoPicker { it, type ->
                    if (type == Keys.FILE_TYPE_IMAGE) {
                        lifecycleScope.launch {
                            val file = Compressor.compress(this@ChatActivity, File(it))
                            val part = RequestBodyRetrofit.toRequestBodyFile(file.absolutePath)
                            viewModel.uploadFile(part)
                        }
                    }

                    if (type == Keys.FILE_TYPE_FILE) {
                        val part = RequestBodyRetrofit.toRequestBodyFile(it)
                        viewModel.uploadFile(part)

                    }
                    if (type == Keys.FILE_TYPE_VIDEO) {

//                        val part = RequestBodyRetrofit.toRequestBodyFileVideo(it)
//                        viewModel.uploadFile(part)
                        val newFile = File(File(it).parentFile, "/compressed_${File(it).name}")
                        if (!newFile.exists()) {
                            newFile.createNewFile()
                        }

                        VideoCompress.compressVideoLow(
                            it,
                            newFile.path,
                            object : VideoCompress.CompressListener {
                                override fun onStart() {
                                    runOnUiThread {
                                        showToast("Compressing...")
                                    }

                                }

                                override fun onSuccess() {
                                    runOnUiThread {
                                        val part =
                                            RequestBodyRetrofit.toRequestBodyFileVideo(newFile.path)
                                        viewModel.uploadFile(part)
                                    }

                                }

                                override fun onFail() {
                                    runOnUiThread {
                                        showToast("Failed to compress...")
                                    }
                                }

                                override fun onProgress(percent: Float) {
//                                    runOnUiThread {
//                                        showToast(+percent.toInt().toString()+"%")
//                                    }
                                }
                            })
                    }


                }
            }

        }
//        binding.ivMic.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
//                if (permissions()) {
//
//                    when (p1!!.action) {
//
//                        MotionEvent.ACTION_DOWN -> {
//                            startRecording()
//
//                        }
//                        MotionEvent.ACTION_UP -> {
//                            stopRecording()
//                        }
//                    }
//
//                }
//                return false
//            }
//        })

        binding.recordButton.setRecordView(binding.recordView)
        binding.recordButton.setOnRecordClickListener {
            showToast("recording")
        }

        binding.recordView.setOnRecordListener(object : OnRecordListener {
            override fun onStart() {

                binding.etMessage.visibility = View.INVISIBLE
                if (permissions()) {
                    startRecording()
                }

            }

            override fun onCancel() {
                stopRecording()
                currentFile!!.delete()
                binding.etMessage.visibility = View.VISIBLE
            }

            override fun onFinish(recordTime: Long, limitReached: Boolean) {

                stopRecording()
                binding.etMessage.visibility = View.VISIBLE
                if (currentFile != null) {
                    val part = RequestBodyRetrofit.toRequestBodyFile(currentFile!!.absolutePath)
                    viewModel.uploadFile(part)
                }
            }

            override fun onLessThanSecond() {

                currentFile!!.delete()
                binding.etMessage.visibility = View.VISIBLE
            }
        })
        binding.recordView.setOnBasketAnimationEndListener {

        }


    }

    private fun permissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPerm.launch(
                arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
            return false
        }

        return true
    }

    val requestPerm =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it[android.Manifest.permission.READ_EXTERNAL_STORAGE] == true && it[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] == true && it[android.Manifest.permission.RECORD_AUDIO] == true) {
                startRecording()

            }
        }

    private fun stopRecording() {
        if (recorder != null) {
            recorder!!.stop()
            recorder!!.reset()
            recorder!!.release()
            recorder=null

        }
    }

    private fun getFilename(): String? {
        val filepath = getExternalFilesDir(null)
        val file = File(filepath, Environment.DIRECTORY_MUSIC)
        if (!file.exists()) {
            file.mkdirs()
        }

        return file.absolutePath + "/" + System.currentTimeMillis() + ".m4a"
    }

    private fun startRecording() {
        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this)
        } else {
            MediaRecorder()
        }
        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        currentFile = File(getFilename())
        recorder!!.setOutputFile(currentFile!!.path)
        recorder!!.setOnErrorListener(errorListener)
        recorder!!.setOnInfoListener(infoListener)
        try {
            recorder!!.prepare()
            recorder!!.start()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.connection2!!.close()
    }

    private val errorListener =
        MediaRecorder.OnErrorListener { mr, what, extra -> }

    private val infoListener =
        MediaRecorder.OnInfoListener { mr, what, extra -> }
}


data class model(
    var SenderSK: String,
    var ReceiverSK: String,
    var MessageType: String,
    var Message: String,
    var AuthToken: String,

    )

data class responsemodel(
    var image: String,
    var message: String?,
    var messageType: String,
    var senderSK: String,
    var senton: String,
    var Name: String
)
