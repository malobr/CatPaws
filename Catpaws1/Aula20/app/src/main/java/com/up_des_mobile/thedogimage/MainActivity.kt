package com.up_des_mobile.thedogimage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.up_des_mobile.thedogimage.view.CatApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Criando o NavController
            val navController = rememberNavController()

            MaterialTheme {
                Surface {
                    // Passando o navController para o CatApp
                    CatApp(navController = navController)
                }
            }
        }

        // Exemplo de chamada assíncrona usando lifecycleScope
        lifecycleScope.launch {
            // Chamando a função suspensa dentro da corrotina
            saveImageToGallery("https://exemplo.com/imagem.jpg")
        }
    }

    // Função suspensa para salvar a imagem na galeria
    suspend fun saveImageToGallery(imageUrl: String) {
        try {
            withContext(Dispatchers.IO) {
                // Baixando a imagem da URL
                val inputStream = URL(imageUrl).openStream()
                // Aqui você pode adicionar o código para salvar a imagem
            }
        } catch (e: Exception) {
            e.printStackTrace() // Tratar o erro caso ocorra
        }
    }
}
