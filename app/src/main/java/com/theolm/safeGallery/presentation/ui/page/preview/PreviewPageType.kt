package com.theolm.safeGallery.presentation.ui.page.preview

import android.net.Uri
import android.os.Parcelable
import com.theolm.core.data.SafePhoto
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class PreviewPageType : Parcelable {
    class NewPhoto(val tempImageUri: Uri) : PreviewPageType()
    class Photo(val photo: SafePhoto) : PreviewPageType()
}

