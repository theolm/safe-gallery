package com.theolm.core.utils

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment


@RunWith(RobolectricTestRunner::class)
internal class AppFileUtilsTest {
    private val utils = AppFileUtils

    @Test
    fun `on getImageFileName, with prefix TEST, expects filename starting with TEST`() {
        val prefix = "TEST"
        val current = utils.getImageFileName(prefix, "??")
        Assert.assertTrue(current.startsWith(prefix))
    }

    @Test
    fun `on getImageFileName, with prefix IMAGE, expects filename starting with IMAGE`() {
        val prefix = "IMAGE"
        val current = utils.getImageFileName(prefix, "??")
        Assert.assertTrue(current.startsWith(prefix))
    }

    @Test
    fun `on getImageFileName, with extension EXE, expects filename ending with dotEXE`() {
        val extension = "EXE"
        val current = utils.getImageFileName("prefix", extension)
        Assert.assertTrue(current.endsWith(".$extension"))
    }

    @Test
    fun `on getImageFileName, with extension JPG, expects filename ending with dotJPG`() {
        val extension = "JPG"
        val current = utils.getImageFileName("prefix", extension)
        Assert.assertTrue(current.endsWith(".$extension"))
    }

    @Test
    fun `on getImageFileName twice with same arguments, should return different strings`() {
        val first = utils.getImageFileName("prefix", "extension")
        val second = utils.getImageFileName("prefix", "extension")
        Assert.assertNotEquals(first, second)
    }

    @Test
    fun `on createTempFile with extension == JPG, expects a File with extension dotJPG`() {
        val extension = "JPG"
        val context = RuntimeEnvironment.getApplication()
        val file = utils.createTempFile(context, extension)
        Assert.assertTrue(file.name.endsWith(".$extension"))
    }

    @Test
    fun `on createTempFile with extension == PNG, expects a File with extension dotPNG`() {
        val extension = "PNG"
        val context = RuntimeEnvironment.getApplication()
        val file = utils.createTempFile(context, extension)
        Assert.assertTrue(file.name.endsWith(".$extension"))
    }

    @Test
    fun `on createTempFile should use cache directory`() {
        val extension = "PNG"
        val context = RuntimeEnvironment.getApplication()
        val file = utils.createTempFile(context, extension)
        Assert.assertTrue(file.path.contains("cache"))
    }

    @Test
    fun `getImagesFolder should return a folder that exists`() {
        val context = RuntimeEnvironment.getApplication()
        val folder = utils.getImagesFolder(context)
        Assert.assertTrue(folder.exists())
        Assert.assertTrue(!folder.isFile)
    }

    @Test
    fun `expects getImagesFolder returns a folder in the private app files in the subdirectory images`() {
        val context = RuntimeEnvironment.getApplication()
        val folder = utils.getImagesFolder(context)
        Assert.assertTrue(folder.path.contains("files/images"))
    }
}