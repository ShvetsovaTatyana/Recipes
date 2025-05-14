package com.github.ilyashvetsov.recipes

import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import java.io.IOException

 fun loadImageFromAssets(fileName: String, imageView: ImageView) {
    val context = imageView.context
    val assetManager = context.assets
    try {
        val inputStream = assetManager.open(fileName)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        imageView.setImageBitmap(bitmap)
        inputStream.close()
    } catch (e: IOException) {
        Log.e("ImageLoadError", "Image not found: $fileName", e)
    }
}