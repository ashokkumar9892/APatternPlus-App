package com.example.patternclinic.home.drawerFragments.messages

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Base64
import android.util.Log
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
import com.example.patternclinic.databinding.BottomSheetCameraGalleryBinding
import com.example.patternclinic.home.drawerFragments.messages.zoomPackage.CodeChallengeHelper
import com.example.patternclinic.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.microsoft.signalr.Action1
import com.vincent.videocompressor.VideoCompress
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject


@Suppress("INACCESSIBLE_TYPE")
@AndroidEntryPoint
class ChatActivity : BaseActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var chatAdapter: ChatAdapter

    @Inject
    lateinit var okHttpClient: OkHttpClient

    //    var connection2: HubConnection? = null
    val viewModel: ChatActivityViewModel by viewModels()
    var recorder: MediaRecorder? = null
    var map: HashMap<String, Any>? = null
    var userDetail: LoginResponse? = null
    var receiverUser: ChatInfo? = null
    var receiverSk: String? = null
    var notificationData: MutableMap<String, Any>? = null
    var currentFile: File? = null
    var pickerDialog: BottomSheetDialog? = null
    var latestFile: Uri? = null
    private val pickImageGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                val file = FileUtils.getFile(this, it)
                lifecycleScope.launch {
                    val compressFile = Compressor.compress(this@ChatActivity, file!!)
                    val part = RequestBodyRetrofit.toRequestBodyFile(compressFile.path)
                    viewModel.uploadFile(part)
                }
                Log.e("file dir", file?.path.toString())
            }
        }

    private val docPicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                val file = FileUtils.getFile(this, it)

                val part = RequestBodyRetrofit.toRequestBodyFile(file?.path)
                viewModel.uploadFile(part)

                Log.e("file dir", file?.path.toString())
            }
        }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            val name = File(latestFile.toString()).name
            val file = File(getExternalFilesDir(null), "/$name")
            lifecycleScope.launch {
                val compressFile = Compressor.compress(this@ChatActivity, file)
                val part = RequestBodyRetrofit.toRequestBodyFile(compressFile.path)
                viewModel.uploadFile(part)
            }
        }
    }

    private val pickVideo = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            val file = FileUtils.getFile(this, it)
            val newFile = File(file?.parentFile, "/compressed_${file?.name}")
            if (!newFile.exists()) {
                newFile.createNewFile()
            }
            VideoCompress.compressVideoLow(
                file?.path,
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


    private val videoLauncher = registerForActivityResult(ActivityResultContracts.CaptureVideo()) {
        if (it) {
            val name = File(latestFile!!.toString()).name
            val file = File(getExternalFilesDir(null), "/$name")
//            lifecycleScope.launch {
//                var compressFile = Compressor.compress(this@ChatActivity, file)
//                val part = RequestBodyRetrofit.toRequestBodyFile(compressFile.path)
//                viewModel.uploadFile(part)
//            }
            val newFile = File(file.parentFile, "/compressed_${file.name}")
            if (!newFile.exists()) {
                newFile.createNewFile()
            }

            VideoCompress.compressVideoLow(
                file.path,
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

                    }
                })


        }
    }

    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        userDetail = SharedPrefs.getLoggedInUser()
        viewModel.binding = binding

        if (intent.hasExtra(Keys.NOTIFICATIONS)) {
            notificationData =
                Gson().fromJson<MutableMap<String, Any>>(
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
//        helperInit()
        viewModel.initializeSdk(this)
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

    private fun helperInit() {
//        val codeChallengeHelper = CodeChallengeHelper()
//        codeChallengeHelper.createCodeVerifier()
//        codeChallengeHelper.createCodeChallenge(CodeChallengeHelper.verifier!!)
//        val uri = Uri.parse("https://zoom.us/oauth/authorize")
//            .buildUpon()
//            .appendQueryParameter("code_challenge",CodeChallengeHelper.verifier!!)
//            .appendQueryParameter("client_id", "eOWmqYNhRsSgnZ9gYbvRw")
//            .appendQueryParameter("code_challenge_method", "S256")
//            .appendQueryParameter("response_type", "code")
//            .appendQueryParameter("redirect_uri", "https://annexappapi.apatternplus.com/zoomlink.html")
//            .build()
//        val intent = Intent(Intent.ACTION_VIEW, uri)
//        startActivity(intent)
//        requestAccessToken(CodeChallengeHelper.verifier!!, okHttpClient)
    }

//    private fun requestAccessToken(code: String, client: OkHttpClient) {
//        lifecycleScope.launch(Dispatchers.IO) {
//        val encoded = Base64.encodeToString(
//            "eOWmqYNhRsSgnZ9gYbvRw:4RA00QT1WfMmL2lLGvjMN7oUDyYJl25S".toByteArray(),
//            Base64.URL_SAFE or Base64.NO_WRAP
//        )
//        val request = Request.Builder()
//            .addHeader("Authorization", "Basic $encoded")
//            .addHeader("Content-Type", "application/x-www-form-urlencoded")
//            .url(buildUrl(code))
//            .post("{}".toRequestBody())
//            .build()
//        val response = client.newCall(request).execute()
//            Log.e("token", response.body.toString())
//        }
//
////        accessToken = Json.decodeFromString<AccessToken>(body.orEmpty()).access_token
//
//    }
//
//    private fun buildUrl(code: String) = HttpUrl.Builder()
//        .scheme("https")
//        .host("zoom.us")
//        .addPathSegment("oauth")
//        .addPathSegment("token")
//        .addQueryParameter("grant_type", "authorization_code")
//        .addQueryParameter("code", code)
//        .addQueryParameter("redirect_uri", "https://annexappapi.apatternplus.com/zoomlink.html")
//        .addQueryParameter("code_verifier", CodeChallengeHelper.verifier)
//        .build()
//
//
//    data class AccessToken(
//        val access_token: String,
//        val token_type: String,
//        val refresh_token: String,
//        val expires_in: Long,
//        val scope: String
//    )
//

    fun setObservers() {
        /**
         * observer for sign up response
         */

        viewModel.chatData.observe(this) {
            if (it.response == 1) {

                val a = it.chatlist.reversed()
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

    @SuppressLint("ClickableViewAccessibility", "CheckResult")
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

                    }
                } catch (e: Exception) {
                    showToast(e.toString())
                    viewModel.makeConnection()
                }
            }
        }
        binding.ivVideoCall.setOnClickListener {
            viewModel.joinMeeting(this,"","")
        }

        binding.ivBack.setOnClickListener {

            finish()
        }
        binding.ivDoc.setOnClickListener {
            docPicker.launch("application/pdf")
        }
        binding.ivCamera.setOnClickListener {

            pickerDialog = BottomSheetDialog(this)
            val bind = BottomSheetCameraGalleryBinding.inflate(layoutInflater)
            pickerDialog?.setContentView(bind.root)
            bind.tvCamera.setOnClickListener {

                pickerDialog?.dismiss()
                latestFile = getTmpFileUri(this@ChatActivity)
                cameraLauncher.launch(latestFile)
            }


            bind.tvImageGallery.setOnClickListener {

                latestFile = getTmpFileUri(this@ChatActivity)
                pickImageGalleryLauncher.launch("image/*")
                pickerDialog?.dismiss()
            }


            bind.tvRecordVideo.setOnClickListener {
                latestFile = getVideoTmpFileUri(this)
                videoLauncher.launch(latestFile)
                pickerDialog?.dismiss()
            }
            bind.tvVideoGallery.setOnClickListener {
                pickVideo.launch("video/*")
                pickerDialog?.dismiss()
            }
            if (permissions()) {
                pickerDialog?.show()
            }


        }

        binding.recordButton.setRecordView(binding.recordView)
        binding.recordButton.setOnRecordClickListener {
            showToast("recording")
        }

        binding.recordView.setOnRecordListener(object : OnRecordListener {
            override fun onStart() {
                binding.etMessage.visibility = View.INVISIBLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (permissions()) {
                        startRecording()
                    }

                } else {
                    startRecording()
                }
            }

            override fun onCancel() {
                stopRecording()
                currentFile!!.delete()
                binding.etMessage.visibility = View.VISIBLE
            }

            override fun onFinish(recordTime: Long, limitReached: Boolean) {


                binding.etMessage.visibility = View.VISIBLE
                if (currentFile != null) {
                    stopRecording()
                    val part = RequestBodyRetrofit.toRequestBodyFile(currentFile!!.absolutePath)
                    viewModel.uploadFile(part)
                }

            }

            override fun onLessThanSecond() {
                if (currentFile != null) {
                    currentFile!!.delete()
                    binding.etMessage.visibility = View.VISIBLE
                }
            }
        })
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
            || ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPerm.launch(
                arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA,
                )
            )
            return false
        }

        return true
    }

    private val requestPerm =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it[android.Manifest.permission.READ_EXTERNAL_STORAGE] == true && it[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] == true && it[android.Manifest.permission.RECORD_AUDIO] == true) {
//                startRecording()
                Log.e("permissions", "accepted")

            }
        }

    private fun stopRecording() {
        if (recorder != null) {
            recorder!!.stop()
            recorder!!.reset()
            recorder!!.release()
            recorder = null

        }
    }

    private fun getFilename(): String {
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
