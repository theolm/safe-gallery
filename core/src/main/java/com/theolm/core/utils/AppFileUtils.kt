package com.theolm.core.utils

import android.content.Context
import java.io.File
import java.util.*

object AppFileUtils {
    private const val images = "images"
    private const val tempName = "tmp_file"

    fun getImagesFolder(context: Context): File {
        val folder = File(context.filesDir, images)
        if (!folder.exists()) folder.mkdir()

        return folder
    }

    fun getImageFileName(prefix: String, extension: String): String {
        val date = Date().toString().replace(" ", "_").replace(":", "-")
        return "$prefix$date$extension"
    }

    fun createTempFile(context: Context, extension: String): File =
        File.createTempFile(tempName, extension, context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
}