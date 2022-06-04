package com.example.patternclinic.home.drawerFragments.appointments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.widget.DatePicker
import android.widget.RadioButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.api.ApiService
import com.example.patternclinic.data.model.AppointmentListResponse
import com.example.patternclinic.data.model.BasicResponse
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.databinding.ActivityAddAppointmentBinding
import com.example.patternclinic.retrofit.ResponseResult
import com.example.patternclinic.retrofit.getResult
import com.example.patternclinic.utils.SharedPrefs
import com.example.patternclinic.utils.dateConvert_3
import com.example.patternclinic.utils.showToast
import com.example.patternclinic.utils.timeConvert24to12
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class AddAppointmentViewModel @Inject constructor(private val service: ApiService) :
    BaseViewModel() {

    val createAppointmentResponse = MutableLiveData<BasicResponse>()
    var addBinding: ActivityAddAppointmentBinding? = null
    var date: Calendar? = null
    var userDetail: LoginResponse? = null

    init {
        userDetail = SharedPrefs.getLoggedInUser()
        date = Calendar.getInstance()
    }


    /**
     * below method used for create appointment
     */

    fun createAppointment(map: HashMap<String, Any>) {
        showLoader.value = true
        job = viewModelScope.launch {
            val result = getResult({ service.createAppointment(map) }, "createAppointment")
            when (result) {
                is ResponseResult.SUCCESS -> {
                    val response = (result.result.data as BasicResponse)
                    createAppointmentResponse.postValue(response)
                }
                is ResponseResult.ERROR -> {
                    errorMessage.postValue(result.result.errorMsg.toString())
                }
                is ResponseResult.FAILURE -> {
                    errorMessage.postValue(result.toString())
                }
            }
            showLoader.value = false
        }
    }

    fun click(v: View) {
        when (v.id) {
            R.id.tv_date -> {
                calenderDialog(v.context)
            }
            R.id.tv_time -> {
                timePicker(v.context)
            }
            R.id.btn_submit_reset -> {
                if (addBinding!!.tvDate.text.trim().isEmpty()) {
                    v.context.showToast(v.resources.getString(R.string.select_date))
                } else if (addBinding!!.tvTime.text.trim().isEmpty()) {
                    v.context.showToast(v.resources.getString(R.string.select_time))
                } else if (addBinding!!.rgGroup.checkedRadioButtonId == R.id.rb_in_person && addBinding!!.tvLocation.text.trim()
                        .toString().isEmpty()
                ) {
                    v.context.showToast(v.resources.getString(R.string.select_location))
                } else {
                    val map = HashMap<String, Any>()
                    map[ApiConstants.APIParams.APPOINTMENT_DATE.value] =
                        addBinding!!.tvDate.text.trim().toString()
                    map[ApiConstants.APIParams.APPOINTMENT_TYPE.value] =
                        addBinding!!.root.findViewById<RadioButton>(addBinding!!.rgGroup.checkedRadioButtonId).text
                    map[ApiConstants.APIParams.APPOINTMENT_TIME.value] =
                        addBinding!!.tvTime.text.trim().toString()
                    if (addBinding!!.rgGroup.checkedRadioButtonId == R.id.rb_in_person) {
                        map[ApiConstants.APIParams.LOCATION.value] =
                            addBinding!!.tvLocation.text.trim().toString()
                    }
                    map[ApiConstants.APIParams.AUTH_TOKEN.value] = userDetail!!.authToken
                    map[ApiConstants.APIParams.DOCTOR_SK.value] =
                        AddAppointmentActivity.team!!.coachSK
                    map[ApiConstants.APIParams.PATIENT_SK.value] = userDetail!!.patientInfo.sk
                    createAppointment(map)
                }
            }
        }
    }

    private fun calenderDialog(context: Context?) {
        val datePicker =
            DatePickerDialog(
                context!!,
                object : DatePickerDialog.OnDateSetListener {
                    @SuppressLint("SetTextI18n")
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                        var day = p3.toString()
                        var month = (p2 + 1).toString()
                        if (day.length != 2) {
                            day = "0$day"
                        }
                        if (month.length != 2) {
                            month = "0${month}"
                        }

                        addBinding!!.tvDate.text = "${day}-${month}-${p1}"
                    }
                },
                date!!.get(Calendar.YEAR),
                date!!.get(Calendar.MONTH),
                date!!.get(Calendar.DAY_OF_MONTH)
            )
        datePicker.datePicker.minDate = Date().time + 86400000

        datePicker.show()
    }

    private fun timePicker(context: Context?) {

        val datePicker = TimePickerDialog(
            context!!,
            TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                addBinding!!.tvTime.text = timeConvert24to12("${i}:${i2}")
            },
            date!!.get(Calendar.HOUR_OF_DAY),
            date!!.get(Calendar.MINUTE),
            false
        )
        datePicker.show()
    }
}