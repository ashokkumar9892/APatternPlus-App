package com.example.patternclinic.utils

import android.Manifest

object PermissionConstant {

    val cameraGalleryPermissionList = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}