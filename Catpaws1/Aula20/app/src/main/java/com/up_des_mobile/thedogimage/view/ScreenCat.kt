package com.up_des_mobile.thedogimage.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.up_des_mobile.thedogimage.model.Cat
import com.up_des_mobile.thedogimage.model.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.ui.platform.LocalContext

@Composable
fun CatApp() {
    var catImage by remember { mutableStateOf("") }
    var showShareButton by remember { mutableStateOf(false) }
    val context = LocalContext.current

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFCE4EC), Color(0xFFF8BBD0))
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Título
            Text(
                text = "Adote um Gato!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF880E4F)
                ),
                textAlign = TextAlign.Center
            )

            // Botão para buscar imagem
            Button(
                onClick = {
                    val apiKey = "live_5GxDaiLmmgOlFdJFyC7xwhF1rY8OTKLdZ1XTXshTGPsUWM5Qpy6xW6ifOqqURIWe"
                    RetrofitInstance.api.getCat(apiKey).enqueue(object : Callback<List<Cat>> {
                        override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                            if (response.isSuccessful) {
                                catImage = response.body()?.firstOrNull()?.url ?: ""
                                showShareButton = true
                            }
                        }

                        override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                            // Falha Handle
                        }
                    })
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAD1457)),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)
            ) {
                Icon(Icons.Filled.Pets, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Buscar Gato",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                )
            }

            // Exibição da imagem e botão de compartilhamento
            if (catImage.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Imagem com animação de carregamento
                        SubcomposeAsyncImage(
                            model = catImage,
                            contentDescription = "Imagem do gato",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {
                            SubcomposeAsyncImageContent()
                            if (painter.state is coil.compose.AsyncImagePainter.State.Loading) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }

                        if (showShareButton) {
                            Button(
                                onClick = { shareImage(catImage, context) },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF880E4F)),
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth(0.8f)
                            ) {
                                Icon(Icons.Filled.Share, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Compartilhar Gato",
                                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun shareImage(imageUrl: String, context: Context) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, imageUrl)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, "Compartilhar imagem"))
}
