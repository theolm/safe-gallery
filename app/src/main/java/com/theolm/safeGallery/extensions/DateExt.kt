package com.theolm.safeGallery.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toDateAndTime() : String {
    val sdf = "MM/dd/yyyy hh:mm:ss"
    val formatter = SimpleDateFormat(sdf, Locale.ROOT)
    return formatter.format(this)
}