
// Archivo: ui/theme/pages/RegistroUbicacion.kt (Refactorizado)
package cl.duoc.nwapp.ui.theme.pages

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duoc.nwapp.viewmodel.HistorialViewModel
import cl.duoc.nwapp.viewmodel.RegistroUbicacionViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroUbicacionScreen(
    navController: NavController,
    historialViewModel: HistorialViewModel, // Aún lo necesitamos para guardar
    registroViewModel: RegistroUbicacionViewModel = viewModel(), // Instancia del nuevo ViewModel
    nombreInicial: String?,
    latitudInicial: String?,
    longitudInicial: String?
) {
    val context = LocalContext.current

    // El estado de la UI (los campos de texto) ahora vive en el `registroViewModel`.
    val ubicacionState = registroViewModel.ubicacion

    // Decodifica y establece los valores iniciales una sola vez.
    LaunchedEffect(Unit) {
        val decodedNombre = if (nombreInicial != null) URLDecoder.decode(nombreInicial, StandardCharsets.UTF_8.name()) else ""
        registroViewModel.setInitialValues(decodedNombre, latitudInicial ?: "", longitudInicial ?: "")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Ubicación") },
                navigationIcon = {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Registrar Nueva Ubicación", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = ubicacionState.nombre,
                onValueChange = { ubicacionState.nombre = it },
                label = { Text("Nombre del Lugar") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = ubicacionState.latitud,
                onValueChange = { ubicacionState.latitud = it },
                label = { Text("Latitud") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = ubicacionState.longitud,
                onValueChange = { ubicacionState.longitud = it },
                label = { Text("Longitud") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    // La UI solo notifica la acción al ViewModel
                    if (registroViewModel.registrarUbicacion(historialViewModel)) {
                        Toast.makeText(context, "Ubicación '${ubicacionState.nombre}' registrada", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Error: Datos inválidos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar en Historial")
            }
        }
    }
}
