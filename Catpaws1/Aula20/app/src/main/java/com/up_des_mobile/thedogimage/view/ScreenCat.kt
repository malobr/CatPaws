package com.up_des_mobile.thedogimage.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Adote um Gato!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "Buscar Gato", style = MaterialTheme.typography.bodyMedium)
            }

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
                        Image(
                            painter = rememberImagePainter(catImage),
                            contentDescription = "Imagem do gato",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(8.dp)
                        )

                        if (showShareButton) {
                            Button(
                                onClick = { shareImage(catImage, context) },
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth(0.8f)
                            ) {
                                Text(text = "Compartilhar Gato", style = MaterialTheme.typography.bodyMedium)
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
