package com.theolm.core.utils

import android.content.Context
import java.io.File
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

object AppFileUtils {
    private const val images = "images"
    private const val tempName = "tmp_file"

    fun getImagesFolder(context: Context): File {
        val folder = File(context.filesDir, images)
        if (!folder.exists()) folder.mkdir()

        return folder
    }

    fun getImageFileName(prefix: String, extension: String): String {
        val date = Date().time
        val random = Random.nextInt(IntRange(start = 1, endInclusive = Int.MAX_VALUE))
        return "${prefix}_${date}_$random.$extension"
    }

    fun createTempFile(context: Context, extension: String): File =
        File.createTempFile("${tempName}_", ".$extension", context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
}