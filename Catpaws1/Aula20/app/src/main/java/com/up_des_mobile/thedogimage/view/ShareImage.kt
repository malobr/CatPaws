package com.up_des_mobile.thedogimage.view

import android.content.Context
import android.content.Intent

fun shareImage(imageUrl: String, context: Context) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, imageUrl)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, "Compartilhar imagem"))
}
