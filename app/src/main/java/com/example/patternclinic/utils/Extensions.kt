package com.example.patternclinic.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.patternclinic.R


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