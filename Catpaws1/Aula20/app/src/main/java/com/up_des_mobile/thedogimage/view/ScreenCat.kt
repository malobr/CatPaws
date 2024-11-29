package com.up_des_mobile.thedogimage.view

import saveImageToGallery
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Delete

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatApp(navController: NavController) {
    var catImage by remember { mutableStateOf("") }
    var showShareButton by remember { mutableStateOf(false) }
    var randomText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var newMessage by remember { mutableStateOf("") }
    var editMessageIndex by remember { mutableStateOf<Int?>(null) }
    var editMessageText by remember { mutableStateOf("") }

    val randomMessages = remember {
        mutableStateListOf(
            "Este é o Arquelau, tem 20 anos e é um verdadeiro companheiro!",
            "Conheça o Vladmir, com 7 anos, pronto para encher sua vida de amor!",
            "Trump, um(a) adorável felino(a) de 12 anos, procura um lar cheio de carinho!",
            "A Ana tem 3 anos e é cheia de energia! Adote e tenha um amigo para toda a vida!",
            "Com 2 anos, Milei é o amigo perfeito para transformar seu lar em um lugar mais feliz!"
        )
    }

    val scrollState = rememberScrollState()

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8D6F1))
                .padding(16.dp)
                .verticalScroll(scrollState), // Permite scroll no conteúdo
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Move o conteúdo para o topo
        ) {
            Text(
                text = "Adote um Gato!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9F7ACD),
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    shadow = Shadow(
                        color = Color(0xFF9F7ACD),
                        offset = Offset(0f, 0f),
                        blurRadius = 8f
                    )
                ),
                modifier = Modifier
                    .padding(bottom = 32.dp)
            )

            Button(
                onClick = {
                    randomText = randomMessages.random()
                    val apiKey =
                        "live_5GxDaiLmmgOlFdJFyC7xwhF1rY8OTKLdZ1XTXshTGPsUWM5Qpy6xW6ifOqqURIWe"

                    scope.launch {
                        try {
                            val cats = RetrofitInstance.api.getCat(apiKey)
                            catImage = cats.firstOrNull()?.url ?: ""
                            showShareButton = true
                            errorMessage = ""
                        } catch (e: Exception) {
                            errorMessage = "Falha na conexão. Verifique sua internet."
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9B7F9))
            ) {
                Icon(
                    Icons.Filled.Pets,
                    contentDescription = null,
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Buscar Gato",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (catImage.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .shadow(12.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = catImage,
                            contentDescription = "Imagem do gato",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            SubcomposeAsyncImageContent()
                            if (painter.state is coil.compose.AsyncImagePainter.State.Loading) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }

                        if (randomText.isNotEmpty()) {
                            Text(
                                text = randomText,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color(0xFF6A4E9F)
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }
                }

                if (showShareButton) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { shareImage(catImage, context) },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(8.dp)
                                .shadow(8.dp, RoundedCornerShape(16.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9F7ACD))
                        ) {
                            Icon(Icons.Filled.Share, contentDescription = "Compartilhar")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Compartilhar",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    saveImageToGallery(catImage, context)
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(8.dp)
                                .shadow(8.dp, RoundedCornerShape(16.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9F7ACD))
                        ) {
                            Icon(Icons.Filled.Download, contentDescription = "Salvar imagem")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Salvar Imagem",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                            )
                        }
                    }
                }
            }

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Gerenciar Mensagens",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9F7ACD)
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                label = { Text("Digite uma nova mensagem") },
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (newMessage.isNotBlank()) {
                        randomMessages.add(newMessage)
                        newMessage = ""
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(0.8f)
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9B7F9))
            ) {
                Text(
                    text = "Adicionar Nova Mensagem",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                )
            }

            randomMessages.forEachIndexed { index, message ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                    )

                    IconButton(onClick = {
                        editMessageIndex = index
                        editMessageText = message
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Pets,
                            contentDescription = "Editar mensagem",
                            tint = Color(0xFF6A4E9F)
                        )
                    }

                    IconButton(onClick = {
                        randomMessages.removeAt(index) // Remove a mensagem
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Deletar mensagem",
                            tint = Color.Red
                        )
                    }
                }
            }

            editMessageIndex?.let { index ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = editMessageText,
                        onValueChange = { editMessageText = it },
                        label = { Text("Editar Mensagem") },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            randomMessages[index] = editMessageText
                            editMessageIndex = null
                            editMessageText = ""
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(0.8f)
                            .shadow(8.dp, RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9B7F9))
                    ) {
                        Text(
                            text = "Salvar Alterações",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                        )
                    }
                }
            }
        }
    }
}
