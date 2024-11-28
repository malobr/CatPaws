import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

// Função para salvar imagem na galeria
suspend fun saveImageToGallery(imageUrl: String, context: Context) {
    try {
        val bitmap = downloadImage(imageUrl, context)
        if (bitmap != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Usando MediaStore para salvar na galeria no Android 10 ou superior
                val contentResolver = context.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, "cat_image_${System.currentTimeMillis()}.jpg")
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/YourApp") // Pasta personalizada
                }

                val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                imageUri?.let { uri ->
                    val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
                    outputStream?.use {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    }
                    // Aciona o scanner para atualizar a galeria
                    MediaScannerConnection.scanFile(context, arrayOf(imageUri.toString()), null, null)
                    Log.d("SaveImage", "Imagem salva com sucesso!")
                }
            } else {
                // Para versões mais antigas, salve no diretório tradicional
                val file = File(context.getExternalFilesDir(null), "cat_image_${System.currentTimeMillis()}.jpg")
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.close()

                // Adiciona o arquivo à galeria
                MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null, null)
                Log.d("SaveImage", "Imagem salva com sucesso!")
            }
        } else {
            Log.e("SaveImage", "Erro: A imagem não foi baixada.")
        }
    } catch (e: IOException) {
        Log.e("SaveImage", "Erro ao salvar a imagem: ${e.message}")
    }
}

// Função para baixar a imagem
suspend fun downloadImage(imageUrl: String, context: Context): Bitmap? {
    val imageLoader = ImageLoader.Builder(context).build()
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .build()

    return try {
        val result = imageLoader.execute(request)
        if (result is SuccessResult) {
            result.drawable.toBitmap()
        } else {
            Log.e("DownloadImage", "Falha ao baixar a imagem.")
            null
        }
    } catch (e: Exception) {
        Log.e("DownloadImage", "Erro ao baixar a imagem: ${e.message}")
        null
    }
}
