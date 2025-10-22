
// Archivo: ui/theme/pages/HistorialScreen.kt
package cl.duoc.nwapp.ui.theme.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.viewmodel.HistorialViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(navController: NavController, historialViewModel: HistorialViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Búsqueda") },
                navigationIcon = {
                    // Botón de flecha para volver atrás
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (historialViewModel.historial.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Text("No hay ubicaciones guardadas.")
                }
            } else {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(historialViewModel.historial) { ubicacion ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = ubicacion.nombre, style = MaterialTheme.typography.headlineSmall)
                                Text(text = "Lat: ${ubicacion.latitud}, Lon: ${ubicacion.longitud}")
                            }
                        }
                    }
                }
            }
        }
    }
}
