package com.example.myapplication.util

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.FileNotFoundException

fun loadBitmapFromUri(context: Context, uri: Uri): ImageBitmap? {
    try {
        // Log the URI to debug potential issues
        println("Loading image from URI: $uri")

        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        return bitmap.asImageBitmap()
    } catch (e: FileNotFoundException) {
        // Log specific error message
        println("File not found: ${e.message}")
    } catch (e: Exception) {
        // Log general exceptions
        e.printStackTrace()
    }
    return null
}
