import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import java.net.URL
import java.io.IOException

// Função para salvar a imagem na galeria
fun saveImageToGallery(imageUrl: String, context: Context) {
    try {
        // Baixa a imagem da URL
        val inputStream = URL(imageUrl).openStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)

        // Salva a imagem no armazenamento do dispositivo
        saveBitmapToGallery(bitmap, context)
    } catch (e: IOException) {
        e.printStackTrace() // Tratamento de erro
        Toast.makeText(context, "Erro ao baixar a imagem!", Toast.LENGTH_SHORT).show()
    }
}

// Função que salva o Bitmap na galeria
private fun saveBitmapToGallery(bitmap: Bitmap, context: Context) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Para Android 10 (API 29) e superior
            val contentResolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "cat_image_${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/AdoteUmGato") // Local de salvamento
            }

            // Insere a imagem no MediaStore
            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                contentResolver.openOutputStream(it).use { outputStream ->
                    if (outputStream != null) {
                        // Comprime e salva a imagem
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                }
                Toast.makeText(context, "Imagem salva na galeria!", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Para Android abaixo do Android 10 (API 29)
            val path = android.os.Environment.getExternalStoragePublicDirectory(
                android.os.Environment.DIRECTORY_PICTURES
            ).toString() + "/AdoteUmGato/"

            val dir = java.io.File(path)
            if (!dir.exists()) {
                dir.mkdirs() // Cria o diretório se não existir
            }

            val file = java.io.File(dir, "cat_image_${System.currentTimeMillis()}.jpg")
            val fileOutputStream = java.io.FileOutputStream(file)

            // Comprime e salva a imagem
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

            Toast.makeText(context, "Imagem salva na galeria!", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        e.printStackTrace() // Tratamento de erro
        Toast.makeText(context, "Erro ao salvar a imagem!", Toast.LENGTH_SHORT).show()
    }
}
