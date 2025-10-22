
// Archivo: ui/theme/pages/MenuLogin.kt
package cl.duoc.nwapp.ui.theme.pages

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.R
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

@Composable
fun PrimeraPantalla(navController: NavController) {
    // `Column` organiza los elementos verticalmente y los centra en la pantalla.
    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa todo el espacio disponible.
        verticalArrangement = Arrangement.Center, // Centra el contenido verticalmente.
        horizontalAlignment = Alignment.CenterHorizontally, // Centra el contenido horizontalmente.
    ) {
        // Mensaje de bienvenida.
        Text("Bienvenido a la Aplicación NWAPP")

        // Imagen del logo.
        Image(
            painter = painterResource(id = R.drawable.nwa), // Carga la imagen desde los recursos.
            contentDescription = "Logo de la APP",
            contentScale = ContentScale.Crop, // Escala la imagen para que ocupe el espacio sin distorsionarse.
            modifier = Modifier.height(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón que navega a la siguiente pantalla.
        Button(onClick = {
            // Al hacer clic, se llama a `navigate` con la ruta de la pantalla de destino.
            navController.navigate("pagina2")
        }) {
            Text("Logearse")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- FIX --- //
        // 1. Se crea un ImageLoader que soporta GIFs.
        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        // 2. El AsyncImage ahora está dentro del Column y usa el loader correcto.
        AsyncImage(
            model = R.drawable.jesus, // El nombre de tu archivo GIF
            contentDescription = "Animación de mapa del mundo",
            imageLoader = imageLoader, // Le pasamos el cargador de GIFs
            modifier = Modifier.height(200.dp),
        )
    }
}
