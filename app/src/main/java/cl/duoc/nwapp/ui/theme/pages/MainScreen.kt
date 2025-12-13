package cl.duoc.nwapp.ui.theme.pages

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.* // Importa todos los layouts básicos de Compose (Column, Row, Spacer, etc.).
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.* // Importa los elementos clave del runtime de Compose (Composable, remember, State, etc.).
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext // Provee el contexto, necesario para el ImageLoader de Coil.
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.R // Necesario para acceder a recursos como el GIF en `R.drawable`.
import cl.duoc.nwapp.viewmodel.DatosViewModel
// Dependencias de Coil para cargar el GIF.
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
// Dependencias de Google Maps para Compose.
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MainScreen(navController: NavController, viewModel: DatosViewModel) {
    // `collectAsState` se suscribe al Flow de `datos` del ViewModel. Cada vez que la lista
    // de ubicaciones en la BD cambia, `ubicaciones` se actualiza y la UI se recompone.
    val ubicaciones by viewModel.datos.collectAsState()
    val context = LocalContext.current

    // Se crea y recuerda un `ImageLoader` específico para GIFs. Es importante hacerlo una vez
    // y recordarlo para no crearlo en cada recomposición, lo que sería ineficiente.
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                // Se añade un decodificador para GIFs. `ImageDecoderDecoder` es la forma moderna
                // para APIs 28+.
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                }
            }
            .build()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp)) // Espacio vertical.

        val miUbicacion = LatLng(-33.49936500787212, -70.61654033901539)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(miUbicacion, 15f) // Posición y zoom inicial.
        }

        GoogleMap(
            modifier = Modifier.weight(1f),
            cameraPositionState = cameraPositionState
        ) {
            ubicaciones.forEach { ubicacion ->
                val lat = ubicacion.latitud.toDoubleOrNull() ?: 0.0
                val lon = ubicacion.longitud.toDoubleOrNull() ?: 0.0
                Marker(
                    state = MarkerState(position = LatLng(lat, lon)),
                    title = ubicacion.nombre
                )
            }

            Marker(
                state = MarkerState(position = LatLng(-33.497672632070476, -70.6126025410391)),
                title = "Lugar 1"
            )
            Marker(
                state = MarkerState(position = LatLng(-33.50104607891704, -70.61707122623334)),
                title = "Lugar 2"
            )
            Marker(
                state = MarkerState(position = LatLng(-33.49774554586376, -70.6178305190539)),
                title = "Lugar 3"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fila para los botones principales
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Button(onClick = { navController.navigate("pagina4") }) {
                Text("Registrar Ubicación")
            }

            Button(onClick = {
                navController.navigate("pagina1") { popUpTo("pagina1") { inclusive = true } }
            }) {
                Text("Regresar al Login")
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espacio menor

        // Botón para ir al Historial
        Button(onClick = { navController.navigate("historial") }) {
            Text("Ver Historial")
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espacio menor

        // Botón para el Manual de Usuario
        Button(onClick = { navController.navigate("manual") }) {
            Text("Manual de Usuario")
        }

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = R.drawable.ep10cingray,
            contentDescription = "Animación de mapa del mundo",
            imageLoader = imageLoader,
            modifier = Modifier.fillMaxWidth().height(150.dp) // Reducimos un poco la altura para dar espacio
        )
    }
}
