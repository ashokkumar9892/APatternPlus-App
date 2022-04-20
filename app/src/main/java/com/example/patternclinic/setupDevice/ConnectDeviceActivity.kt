package com.example.patternclinic.setupDevice

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ActivityConnectDeviceBinding
import com.example.patternclinic.home.HomeScreenActivity
import com.example.patternclinic.selectTeam.SelectPatternPlusTeam
import com.example.patternclinic.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.inuker.bluetooth.library.Code
import com.inuker.bluetooth.library.Constants
import com.inuker.bluetooth.library.search.SearchResult
import com.inuker.bluetooth.library.search.response.SearchResponse
import com.inuker.bluetooth.library.utils.BluetoothUtils
import com.veepoo.protocol.VPOperateManager
import com.veepoo.protocol.listener.base.IABleConnectStatusListener
import com.veepoo.protocol.listener.base.IABluetoothStateListener
import com.veepoo.protocol.model.enums.ELanguage
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat


class ConnectDeviceActivity : AppCompatActivity(), OnItemClicked {
    var binding: ActivityConnectDeviceBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_connect_device)
        initDesign()

        connectDeivce()
        checkPermission()
        registerBluetoothStateListener()
        scanDevice()

    }

    lateinit var vpo: VPOperateManager
    private fun connectDeivce() {
        vpo = VPOperateManager.getMangerInstance(this)
        var scanner = BluetoothLeScannerCompat.getScanner()

    }

    private fun registerBluetoothStateListener() {
        vpo.registerBluetoothStateListener(mBluetoothStateListener)
    }

    var list = mutableListOf<String>()
    var listDetail = mutableListOf<SearchResult>()
    private fun scanDevice(): Boolean {
//        if (!mListAddress.isEmpty()) {
//            mListAddress.clear()
//        }
//        if (!mListData.isEmpty()) {
//            mListData.clear()
//            bleConnectAdatpter.notifyDataSetChanged()
//        }
        if (!BluetoothUtils.isBluetoothEnabled()) {
            Toast.makeText(this, "off mode", Toast.LENGTH_SHORT).show()
            return true
        }
        //        startScan();
        vpo.startScanDevice(object : SearchResponse {
            override fun onSearchStarted() {

                showToast("searching")
            }

            override fun onDeviceFounded(device: SearchResult) {

//                showToast("${device?.device} found")
                runOnUiThread {
                    if (!list.contains("${device.name}-${device.address}")) {
                        listDetail.add(device)
                        list.add("${device.name}-${device.address}")
                    }
//                    Collections.sort(listDetail, DeviceCompare())
//                    bleConnectAdatpter.notifyDataSetChanged()
                    setAdapter(list)
                }

            }

            fun setAdapter(list: List<String>) {
                var adapter = ConnectDeviceAdapter(list, this@ConnectDeviceActivity)
                binding!!.rvDevices.adapter = adapter
            }

            override fun onSearchStopped() {
                showToast("stoped")
            }

            override fun onSearchCanceled() {
                showToast("cancel")
            }
        })
        return false
    }

    private val mBluetoothStateListener: IABluetoothStateListener =
        object : IABluetoothStateListener() {
            override fun onBluetoothStateChanged(openOrClosed: Boolean) {
//                Logger.t(MainActivity.TAG).i("open=$openOrClosed")
            }
        }

    private fun checkPermission() {

        if (Build.VERSION.SDK_INT <= 22) {
            initBLE()
            return
        }
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//            Logger.t(MainActivity.TAG).i("checkPermission,PERMISSION_GRANTED")
            initBLE()
        } else if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            requestPermission()
//            Logger.t(MainActivity.TAG).i("checkPermission,PERMISSION_DENIED")
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_ADMIN
            ) !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_PRIVILEGED
            ) !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                showToast("done")
            } else {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_PRIVILEGED,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE
                    ),
                    1000
                )
            }
        } else {
            showToast("rejected")
        }
    }

    private fun connectDevice(mac: String, deviceName: String) {
        vpo.registerConnectStatusListener(mac, mBleConnectStatusListener)
        vpo.connectDevice(mac, deviceName,
            { code, profile, isoadModel ->
                if (code == Code.REQUEST_SUCCESS) {
                    //蓝牙与设备的连接状态
//                    Logger.t(MainActivity.TAG).i("连接成功")
//                    Logger.t(MainActivity.TAG).i("是否是固件升级模式=$isoadModel")
                    showToast(isoadModel.toString())
//                    mIsOadModel = isoadModel
//                    isStartConnecting = true
                } else {
//                    Logger.t(MainActivity.TAG).i("连接失败")
//                    isStartConnecting = false
                }
            }, { state ->
                if (state == Code.REQUEST_SUCCESS) {
                    //蓝牙与设备的连接状态
                    vpo.settingDeviceLanguage(
                        {
                            showToast("writeResponse")
                        }, {
                            var message = "设置语言(英文):\n$it";
                            sendMsg(message, 1)
                        }, ELanguage.ENGLISH
                    )

                    showToast("connected")
//                    isStartConnecting = true
                    binding!!.loader.visibility = View.GONE
                    val intent = Intent(this, HomeScreenActivity::class.java)
//                    intent.putExtra("isoadmodel", mIsOadModel)
//                    intent.putExtra("deviceaddress", mac)
                    startActivity(intent)
                } else {
//                    Logger.t(MainActivity.TAG).i("监听失败，重新连接")
//                    isStartConnecting = false
                }
            })
    }

    private val mBleConnectStatusListener: IABleConnectStatusListener =
        object : IABleConnectStatusListener() {
            override fun onConnectStatusChanged(mac: String, status: Int) {
                if (status == Constants.STATUS_CONNECTED) {
                    showToast("STATUS_CONNECTED")
                } else if (status == Constants.STATUS_DISCONNECTED) {
                    showToast("STATUS_DISCONNECTED")
                }
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {

                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    initBLE()
                } else {

                }
                return
            }
        }
    }

    private fun initBLE() {
        var mBAdapter: BluetoothAdapter? = null
        var mBScanner: BluetoothLeScanner? = null
        var mBManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        if (null != mBManager) {
            mBAdapter = mBManager.getAdapter()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBScanner = mBAdapter!!.bluetoothLeScanner
        }
        checkBLE()
    }

    private fun checkBLE(): Boolean {

        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            false
        } else if (!mBluetoothAdapter.isEnabled) {
            // Bluetooth is not enabled :)
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            startActivityForResult(enableBtIntent, REQUEST_CODE)
            false
        } else {
            // Bluetooth is enabled
            true
        }
//        return if (!BluetoothUtils.isBluetoothEnabled()) {
//            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            startActivityForResult(enableBtIntent, REQUEST_CODE)
//            false
//        } else {
//            true
//        }
    }

    private fun initDesign() {
        binding!!.cvStart.setOnClickListener {
            startActivity(Intent(this, SelectPatternPlusTeam::class.java))
        }
        binding!!.llWatch.setOnClickListener {
            binding!!.layoutDisconnected.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                binding!!.layoutDisconnected.visibility = View.GONE
                binding!!.layoutConnected.visibility = View.VISIBLE

            }, 1000)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                binding!!.layoutConnected.visibility = View.GONE
                binding!!.llWatch.setBackgroundResource(R.drawable.drawable_shape_connect_2)
                binding!!.cvStart.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.color_primary)
//                binding!!.cvStart.setback

            }, 2000)
        }
        binding!!.tvChatBot.setOnClickListener {
            chatBotDialog()
        }
    }

    private fun chatBotDialog() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_chat_bot)
        dialog.show()

    }

    private fun sendMsg(message: String, what: Int) {
        var msg = Message.obtain()
        msg.what = what
        msg.obj = message
        var mHandler = Handler(Looper.getMainLooper())
        mHandler.sendMessage(msg)
    }

    override fun itemClicked(position: Int) {
        binding!!.loader.visibility = View.VISIBLE
        val searchResult: SearchResult = listDetail[position]
        connectDevice(searchResult.address, searchResult.name)
    }
}