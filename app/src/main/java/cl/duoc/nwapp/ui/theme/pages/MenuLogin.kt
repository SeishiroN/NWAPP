
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
// Imports de Coil, la biblioteca para cargar imágenes y GIFs de forma eficiente.
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

/**
 * `PrimeraPantalla` es el Composable que define la pantalla de bienvenida/login.
 * Es el punto de entrada de la aplicación, como se define en `Navegacion.kt`.
 * @param navController El controlador de navegación, necesario para moverse a otras pantallas.
 */
@Composable
fun PrimeraPantalla(navController: NavController) {
    // `Column` organiza sus elementos hijos verticalmente.
    Column(
        modifier = Modifier.fillMaxSize(), // Hace que la columna ocupe toda la pantalla.
        verticalArrangement = Arrangement.Center, // Centra a sus hijos en el eje vertical.
        horizontalAlignment = Alignment.CenterHorizontally, // Centra a sus hijos en el eje horizontal.
    ) {
        // Un simple Composable de texto para mostrar un mensaje de bienvenida.
        Text("Bienvenido a la Aplicación NWAPP")

        // Composable `Image` para mostrar el logo de la aplicación.
        Image(
            painter = painterResource(id = R.drawable.nwa), // Carga la imagen desde `res/drawable`.
            contentDescription = "Logo de la APP", // Texto para accesibilidad.
            contentScale = ContentScale.Crop, // Escala la imagen para llenar el espacio sin deformarla.
            modifier = Modifier.height(150.dp) // Le da una altura fija a la imagen.
        )

        // `Spacer` es un componente invisible que simplemente crea un espacio vacío.
        // Útil para separar visualmente otros componentes.
        Spacer(modifier = Modifier.height(16.dp))

        // Un botón estándar de Material Design.
        Button(onClick = {
            // La acción que se ejecuta al hacer clic. Aquí, se navega a la pantalla de registro.
            // `navController.navigate("pagina2")` busca la ruta "pagina2" en el `NavHost`
            // y muestra el Composable asociado a ella (en este caso, `FormularioCrearCuenta`).
            navController.navigate("pagina2")
        }) {
            Text("Logearse") // El texto que aparece dentro del botón.
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- LÓGICA PARA CARGAR EL GIF ANIMADO ---

        // 1. Se crea un `ImageLoader` que sabe cómo procesar GIFs.
        // Se usa `LocalContext.current` para obtener el contexto de la aplicación.
        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context)
            .components {
                // Dependiendo de la versión de Android, se usa un decodificador diferente.
                if (Build.VERSION.SDK_INT >= 28) {
                    // `ImageDecoderDecoder` es la forma moderna y recomendada.
                    add(ImageDecoderDecoder.Factory())
                } else {
                    // `GifDecoder` es para versiones más antiguas de Android.
                    add(GifDecoder.Factory())
                }
            }
            .build()

        // 2. `AsyncImage` es el Composable de Coil para cargar imágenes de forma asíncrona.
        // Es ideal para GIFs o imágenes de la red.
        AsyncImage(
            model = R.drawable.jesus, // El recurso GIF que debe estar en la carpeta `res/drawable`.
            contentDescription = "Animación de Jesús", // Texto para accesibilidad.
            imageLoader = imageLoader, // Se le pasa el cargador de GIFs que creamos arriba.
            modifier = Modifier.height(200.dp), // Se le da una altura fija al GIF.
        )
    }
}
