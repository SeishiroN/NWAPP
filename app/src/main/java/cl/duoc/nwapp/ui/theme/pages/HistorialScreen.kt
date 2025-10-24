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
import androidx.compose.material3.* // Importa los componentes de Material 3.
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.viewmodel.DatosViewModel // Se utiliza el mismo ViewModel que las otras pantallas para compartir datos.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(navController: NavController, viewModel: DatosViewModel) {
    // Nos suscribimos al flujo de datos del ViewModel. `collectAsState` asegura que la pantalla
    // se actualice automáticamente cada vez que la lista de ubicaciones cambie en la base de datos.
    val historial by viewModel.datos.collectAsState()

    // `Scaffold` es un layout predefinido de Material Design que proporciona una estructura
    // estándar para pantallas, incluyendo una barra superior (TopAppBar), contenido principal, etc.
    Scaffold(
        topBar = {
            // `TopAppBar` es la barra que aparece en la parte superior de la pantalla.
            TopAppBar(
                title = { Text("Historial de Ubicaciones") },
                navigationIcon = {
                    // `IconButton` es un botón optimizado para contener un ícono.
                    IconButton(onClick = { navController.popBackStack() }) { // Vuelve a la pantalla anterior.
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Icono de flecha estándar.
                            contentDescription = "Volver atrás" // Texto para accesibilidad.
                        )
                    }
                }
            )
        }
    ) { paddingValues -> // `paddingValues` contiene el espacio que ocupa la `TopAppBar`.
        // `Box` es un layout simple que apila sus hijos. Aquí se usa para aplicar el padding.
        Box(modifier = Modifier.padding(paddingValues)) {
            // Si la lista de historial está vacía, muestra un mensaje.
            if (historial.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center // Centra el texto en la pantalla.
                ) {
                    Text("No hay ubicaciones guardadas.")
                }
            } else {
                // Si hay datos, usa una `LazyColumn` para mostrarlos de forma eficiente.
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    // `items(historial)` itera sobre la lista y crea un Composable para cada elemento.
                    items(historial) { ubicacion ->
                        // `Card` es un contenedor con sombra, ideal para mostrar ítems en una lista.
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
