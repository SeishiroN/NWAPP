package cl.duoc.nwapp.ui.theme.pages

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.R // Importante para acceder a los recursos
import cl.duoc.nwapp.viewmodel.DatosViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MainScreen(navController: NavController, viewModel: DatosViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val ubicaciones by viewModel.datos.collectAsState()
    val context = LocalContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
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
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Icono de búsqueda",
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        val miUbicacion = LatLng(-33.49936500787212, -70.61654033901539)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(miUbicacion, 15f)
        }

        GoogleMap(
            modifier = Modifier.weight(1f),
            cameraPositionState = cameraPositionState
        ) {
            // Marcadores de la base de datos
            ubicaciones.forEach { ubicacion ->
                val lat = ubicacion.latitud.toDoubleOrNull() ?: 0.0
                val lon = ubicacion.longitud.toDoubleOrNull() ?: 0.0
                Marker(
                    state = MarkerState(position = LatLng(lat, lon)),
                    title = ubicacion.nombre
                )
            }

            // Marcadores de ejemplo (los que estaban fuera de lugar)
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Button(onClick = { navController.navigate("pagina4") }) {
                Text("Registrar Ubicación")
            }

            Button(onClick = {
                navController.navigate("pagina1") {
                    popUpTo("pagina1") { inclusive = true }
                }
            }) {
                Text("Regresar al Login")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = R.drawable.ep10cingray, // Corregido el nombre del GIF
            contentDescription = "Animación de mapa del mundo",
            imageLoader = imageLoader,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
