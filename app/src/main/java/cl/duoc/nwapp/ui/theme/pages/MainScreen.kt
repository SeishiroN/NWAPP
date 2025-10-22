package cl.duoc.nwapp.ui.theme.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.viewmodel.DatosViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MainScreen(navController: NavController, datosViewModel: DatosViewModel) { // <- Recibe el NavController
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Barra de búsqueda
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

        // 2. Mapa de Google
        val miUbicacion = LatLng(-33.49936500787212, -70.61654033901539)
        val nombreLugar1 = "Lugar 1"
        val lugar1 = LatLng(-33.497672632070476, -70.6126025410391)
        val nombreLugar2 = "Lugar 2"
        val lugar2 = LatLng(-33.50104607891704, -70.61707122623334)
        val nombreLugar3 = "Lugar 3"
        val lugar3 = LatLng(-33.49774554586376, -70.6178305190539)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(miUbicacion, 15f)
        }

        GoogleMap(
            modifier = Modifier.height(300.dp).fillMaxWidth(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = lugar1),
                title = nombreLugar1
            )
            Marker(
                state = MarkerState(position = lugar2),
                title = nombreLugar2
            )
            Marker(
                state = MarkerState(position = lugar3),
                title = nombreLugar3
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Button(onClick = { navController.navigate("pagina4") }) {
                Text("Registrar Ubicación")
            }

            Button(onClick = {
                // Navega de vuelta a la pantalla de login
                navController.navigate("pagina1") {
                    popUpTo("pagina1") { inclusive = true }
                }
            }) {
                Text("Regresar al Login")
            }
        }
    }
}
