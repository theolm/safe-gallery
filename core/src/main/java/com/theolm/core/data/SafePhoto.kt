package com.theolm.core.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SafePhoto(
    val uri: Uri,
    val lastModified: Long,
) : Parcelable