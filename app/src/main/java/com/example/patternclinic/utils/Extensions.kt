package com.example.patternclinic.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.PermissionRequest
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import com.example.patternclinic.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.robertlevonyan.components.picker.ItemModel
import com.robertlevonyan.components.picker.ItemType
import com.robertlevonyan.components.picker.PickerDialog
import com.robertlevonyan.components.picker.pickerDialog
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.changeStatusBarColorWhite() {
    val window: Window = window
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, R.color.white)


}

@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.changeStatusBarColor(color: Int) {
    val window: Window = window
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, color)


}

@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.decorStatusBar(shouldChangeStatusBarTintToDark: Boolean) {
    val decor: View = window.decorView
    if (shouldChangeStatusBarTintToDark) {
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        // We want to change tint color to white again.
        // You can also record the flags in advance so that you can turn UI back completely if
        // you have set other flags before, such as translucent or full screen.
        decor.systemUiVisibility = 0
    }
}
fun Context.showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}

val EMAIL_ADDRESS_PATTERN: Pattern by lazy {
    Pattern.compile(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}
fun checkEmail(email: String): Boolean {
    return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
}
fun String.createPartFromString(): RequestBody {
    return this.toRequestBody(MultipartBody.FORM)
}

fun FragmentActivity.imagePicker(returnData: (String) -> Unit) = try {
    pickerDialog {
        setTitle(getString(R.string.app_name))          // String value or resource ID
        setTitleTextSize(22f)
        setTitleTextBold(true)// Text size of title
        setTitleTextColor(R.color.color_primary) // Color of title text
        setListType(PickerDialog.ListType.TYPE_GRID)       // Type of the picker, must be PickerDialog.TYPE_LIST or PickerDialog.TYPE_Grid
        setItems(
            mutableSetOf(
                ItemModel(ItemType.ITEM_CAMERA),
                ItemModel(ItemType.ITEM_GALLERY)
            )
        )          // List of ItemModel-s which should be in picker
    }.setPickerCloseListener { type: ItemType, uris: List<Uri> ->
        // Getting the result
        when (type) {
            ItemType.ITEM_CAMERA -> {
                val link = getMediaFilePathFor(uris.first(), this@imagePicker)
                returnData(link)
            }

            ItemType.ITEM_GALLERY -> {
                val link = getMediaFilePathFor(uris.first(), this@imagePicker)
                returnData(link)
            }
            ItemType.ITEM_VIDEO -> {
                val link = getMediaFilePathFor(uris.first(), this@imagePicker)
                returnData(link)
            }
            ItemType.ITEM_VIDEO_GALLERY -> {
                val link = getMediaFilePathFor(uris.first(), this@imagePicker)
                returnData(link)
            }
            ItemType.ITEM_FILES -> {
                val link = getMediaFilePathFor(uris.first(), this@imagePicker)
                returnData(link)
            }
        }
    }.show()


} catch (e: Exception) {
    e.printStackTrace()
}

//grant permissions
fun Context.grantPermission(permissionList: List<String>, granted: (String) -> Unit) {

    Dexter.withContext(this).withPermissions(
        permissionList
    ).withListener(object : MultiplePermissionsListener {
        override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */

            report.let {
                if (report.areAllPermissionsGranted()) {
                    granted("")

                }
            }
        }

        override fun onPermissionRationaleShouldBeShown(
            p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
            p1: PermissionToken?
        ) {
            p1?.continuePermissionRequest()
        }


    }).withErrorListener {
        Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
    }.check()
}
//get media file
fun getMediaFilePathFor(
    uri: Uri,
    context: Context
): String {
    val returnCursor =
        context.contentResolver.query(uri, null, null, null, null)
    /*
     * Get the column indexes of the data in the Cursor,
     *     * move to the first row in the Cursor, get the data,
     *     * and display it.
     * */
    val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
    returnCursor.moveToFirst()
    val name = returnCursor.getString(nameIndex)
    val size = returnCursor.getLong(sizeIndex).toString()
    val file = File(context.filesDir, name)
    try {
        val inputStream =
            context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        var read = 0
        val maxBufferSize = 1 * 1024 * 1024
        val bytesAvailable = inputStream!!.available()
        //int bufferSize = 1024;
        val bufferSize = bytesAvailable.coerceAtMost(maxBufferSize)
        val buffers = ByteArray(bufferSize)
        while (inputStream.read(buffers).also { read = it } != -1) {
            outputStream.write(buffers, 0, read)
        }
        Log.e("File Size %d", "" + file.length())
        inputStream.close()
        outputStream.close()
        Log.e("File Size %s", file.path)
        Log.e("File Size %d", "" + file.length())
    } catch (e: java.lang.Exception) {
        Log.e("File Size %s", e.message!!)
    }
    return file.path
}
//date conversion

fun dateConvert_3(type: String): String {
    val output = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
    val input = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    val date = input.parse(type)
    val result = output.format(date)
    return result
}
fun dateConvert_4(type: String): String {
    val input = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val output = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
    val date = input.parse(type)
    val result = output.format(date)
    return result
}
fun dateConvert_5(type: String): String {
    val output = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val input = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
    val date = input.parse(type)
    val result = output.format(date)
    return result
}
