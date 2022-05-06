package com.example.patternclinic.auth

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.DatePicker
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseViewModel
import com.example.patternclinic.data.ApiConstants
import com.example.patternclinic.data.model.LoginResponse
import com.example.patternclinic.data.model.UpdateProfileResponse
import com.example.patternclinic.data.repository.MainRepository
import com.example.patternclinic.utils.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.lang.Exception
import java.util.*
import javax.inject.Inject


@HiltViewModel
class UpdateProfileViewModel @Inject constructor(val mainRepository: MainRepository) :
    BaseViewModel() {
    var updateProfileResponse = MutableLiveData<UpdateProfileResponse>()
    var password = ObservableField<String>()
    var email = ObservableField<String>()
    var firstName = ObservableField<String>()
    var lastName = ObservableField<String>()
    var country = ObservableField<String>()
    var dob = ObservableField<String>()
    var gender = ObservableField<Int>()
    var binding = CreateProfile.binding
    var userData: LoginResponse? = null
    var encodedImage: String? = null
    var date = Calendar.getInstance()
    var activity = CreateProfile.activity

    init {

        if (SharedPrefs.getLoggedInUser() != null) {
            userData = SharedPrefs.getLoggedInUser()
            if (!userData!!.patientInfo.userName.isNullOrEmpty()) {
                email.set(userData!!.patientInfo.userName)
            }
            if (!userData!!.patientInfo.firstName.isNullOrEmpty()) {
                firstName.set(userData!!.patientInfo.firstName)
            }
            if (!userData!!.patientInfo.lastName.isNullOrEmpty()) {
                lastName.set(userData!!.patientInfo.lastName)
            }
            if (userData!!.patientInfo.country != null) {
                country.set(userData!!.patientInfo.country.toString())
            }
            if (!userData!!.patientInfo.dob.isNullOrEmpty()) {
//                    dob.set(dateConvert_4(userData!!.patientInfo.dob!!))
                dob.set(userData!!.patientInfo.dob!!)
            }
            if (!userData!!.patientInfo.gender.isNullOrEmpty()) {
                gender.set(
                    binding.root.context.resources.getStringArray(R.array.Gender)
                        .indexOf(userData!!.patientInfo.gender)
                )
            }
            if (!userData!!.patientInfo.profilePic.isNullOrEmpty()) {
                try {
                    encodedImage = userData!!.patientInfo.profilePic.replace("\n", "")
                    val decodedString =
                        Base64.decode(
                            userData!!.patientInfo.profilePic.replace("\n", ""),
                            Base64.DEFAULT
                        )
                    val decodedByte =
                        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    binding.ivUserImage.setImageBitmap(decodedByte)
                } catch (e: Exception) {
                    binding.root.context.showToast(e.printStackTrace().toString())
                }

            }
        }
    }


    fun validate(v: View): Boolean {
        if (email.get()?.trim().isNullOrEmpty()) {
            binding.etEmail.setError(v.context.getString(R.string.enter_email))
            return false
        }
        if (firstName.get()?.trim().isNullOrEmpty()) {
            binding.etFirstName.setError(v.context.getString(R.string.enter_first_name))
            return false
        }
        if (lastName.get()?.trim().isNullOrEmpty()) {
            binding.etLastName.error = v.context.getString(R.string.enter_last_name)
            return false
        }
        if (country.get()?.trim().isNullOrEmpty()) {
            binding.etCountry.error = v.context.getString(R.string.enter_country)
            return false
        }
        if (dob.get()?.trim().isNullOrEmpty()) {
            binding.tvDob.error = v.context.getString(R.string.select_your_dob)
            return false
        }
        if (binding.rgGroup.checkedRadioButtonId == -1) {
            binding.root.context.showToast(binding.root.context.getString(R.string.select_refer))
            return false
        }
        if (encodedImage?.trim().isNullOrEmpty()) {
            binding.root.context.showToast(binding.root.context.getString(R.string.select_image))
            return false
        }
        return true
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.iv_user_image -> {
                v.context.grantPermission(PermissionConstant.cameraGalleryPermissionList) {
                    (v.context as AppCompatActivity).imagePicker {
                        Glide.with(v).load(it).into(binding.ivUserImage)
//                        convertToBase64(it)
                        encodedImage = encodeImage(it)
                    }
                }
            }
            R.id.tv_dob -> {
                calenderDialog(v.context)
            }
            R.id.tv_continue_create_profile -> {
                if (validate(v)) {
                    val map = HashMap<String, Any>()
                    map[ApiConstants.APIParams.EMAIL.value] = email.get()?.trim().toString()
                    map[ApiConstants.APIParams.FIRST_NAME.value] =
                        firstName.get()?.trim().toString()
                    map[ApiConstants.APIParams.LAST_NAME.value] = lastName.get()?.trim().toString()
                    map[ApiConstants.APIParams.COUNTRY.value] = country.get()?.trim().toString()
                    map.put(ApiConstants.APIParams.AUTH_TOKEN.value, userData!!.authToken)
                    map.put(ApiConstants.APIParams.USER_NAME.value, userData!!.patientInfo.userName)
                    map.put(ApiConstants.APIParams.SK.value, userData!!.patientInfo.sk)
                    //todo change this after

                    map.put(ApiConstants.APIParams.PROFILE_PIC.value, encodedImage!!)

                    map[ApiConstants.APIParams.DOB.value] = dob.get()?.trim().toString()
                    map[ApiConstants.APIParams.GENDER.value] =
                        binding.spGender.selectedItem.toString()
                    binding.spGender.selectedItem
                    map.put(
                        ApiConstants.APIParams.REFER_AS.value,
                        binding.root.findViewById<RadioButton>(binding.rgGroup.checkedRadioButtonId).text
                    )

//                    map.put(ApiConstants.APIParams.EMAIL.value, email.get().toString())
//                    map.put(ApiConstants.APIParams.EMAIL.value, email.get().toString())
                    var intent = Intent(v.context, CreateProfileWeight::class.java)
//                    intent.putExtra(Keys.mapKeyProfile, Gson().toJson(map))
                    Keys.updateProfileData = Gson().toJson(map)
                    binding.root.context.startActivity(intent)
                }
            }

        }

    }


    private fun calenderDialog(context: Context?) {
        var datePicker =
            DatePickerDialog(binding.root.context, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                    var day = p3.toString()
                    var month = (p2 + 1).toString()
                    if (day.length != 2) {
                        day = "0$day"
                    }
                    if (month.length != 2) {
                        month = "0${month}"
                    }

                    binding.tvDob.text = dateConvert_3("${day}-${month}-${p1}")
                }
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
        datePicker.datePicker.maxDate = Date().time
        datePicker.show()
    }

//    private fun convertToBase64(it: Bitmap) {
//
//
//        val bm = BitmapFactory.decodeFile(it)
//        val baos = ByteArrayOutputStream()
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
//        val b: ByteArray = baos.toByteArray()
//
//        encodedImage = android.util.Base64.encodeToString(b, Base64.DEFAULT)
//    }

    private fun encodeImage(path: String): String? {

//        val decoder: BitmapRegionDecoder = BitmapRegionDecoder.newInstance(path, false)
//        val region = decoder.decodeRegion(Rect(10, 10, 50, 50), null)
////        val bitmaplow = Bitmap.createScaledBitmap(region, 150, 150, true)
//
//        val baos = ByteArrayOutputStream()
//        region.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val b = baos.toByteArray()
//        return Base64.encodeToString(b, Base64.DEFAULT)
        val imagefile = File(path)
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(imagefile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val bm = BitmapFactory.decodeStream(fis)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val b = baos.toByteArray()
        //Base64.de
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

}