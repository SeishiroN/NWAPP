// Archivo: ui/theme/pages/HistorialScreen.kt
package cl.duoc.nwapp.ui.theme.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.* // Importa los componentes de Material 3.
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.model.Datos
import cl.duoc.nwapp.viewmodel.DatosViewModel // Se utiliza el mismo ViewModel que las otras pantallas para compartir datos.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(navController: NavController, viewModel: DatosViewModel) {
    // Nos suscribimos al flujo de datos del ViewModel. `collectAsState` asegura que la pantalla
    // se actualice automáticamente cada vez que la lista de ubicaciones cambie en la base de datos.
    val historial by viewModel.datos.collectAsState()

    // Estados para el diálogo de edición
    var mostrarDialogoEdicion by remember { mutableStateOf(false) }
    var ubicacionAEditar by remember { mutableStateOf<Datos?>(null) }

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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = ubicacion.nombre, style = MaterialTheme.typography.headlineSmall)
                                    Text(text = "Lat: ${ubicacion.latitud}, Lon: ${ubicacion.longitud}")
                                }
                                IconButton(onClick = {
                                    ubicacionAEditar = ubicacion
                                    mostrarDialogoEdicion = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar ubicación"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo de edición
    if (mostrarDialogoEdicion && ubicacionAEditar != null) {
        EditarUbicacionDialog(
            ubicacion = ubicacionAEditar!!,
            onDismiss = { mostrarDialogoEdicion = false },
            onConfirm = { nombre, latitud, longitud ->
                val ubicacionActualizada = ubicacionAEditar!!.copy(
                    nombre = nombre,
                    latitud = latitud.toString(),
                    longitud = longitud.toString()
                )
                viewModel.actualizarDatos(ubicacionActualizada)
                mostrarDialogoEdicion = false
            }
        )
    }
}

/**
 * Diálogo para editar una ubicación existente.
 * Permite modificar el nombre, latitud y longitud de un registro en la base de datos.
 */
@Composable
fun EditarUbicacionDialog(
    ubicacion: Datos,
    onDismiss: () -> Unit,
    onConfirm: (nombre: String, latitud: Double, longitud: Double) -> Unit
) {
    var nombre by remember { mutableStateOf(ubicacion.nombre) }
    var latitud by remember { mutableStateOf(ubicacion.latitud.toString()) }
    var longitud by remember { mutableStateOf(ubicacion.longitud.toString()) }

    var nombreError by remember { mutableStateOf("") }
    var latitudError by remember { mutableStateOf("") }
    var longitudError by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Ubicación") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        nombreError = if (it.isBlank()) "El nombre es obligatorio" else ""
                    },
                    label = { Text("Nombre") },
                    isError = nombreError.isNotEmpty(),
                    supportingText = { if (nombreError.isNotEmpty()) Text(nombreError) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = latitud,
                    onValueChange = {
                        latitud = it
                        latitudError = when {
                            it.isBlank() -> "La latitud es obligatoria"
                            it.toDoubleOrNull() == null -> "Debe ser un número válido"
                            else -> ""
                        }
                    },
                    label = { Text("Latitud") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = latitudError.isNotEmpty(),
                    supportingText = { if (latitudError.isNotEmpty()) Text(latitudError) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = longitud,
                    onValueChange = {
                        longitud = it
                        longitudError = when {
                            it.isBlank() -> "La longitud es obligatoria"
                            it.toDoubleOrNull() == null -> "Debe ser un número válido"
                            else -> ""
                        }
                    },
                    label = { Text("Longitud") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = longitudError.isNotEmpty(),
                    supportingText = { if (longitudError.isNotEmpty()) Text(longitudError) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Validar campos antes de confirmar
                    val nombreValido = nombre.isNotBlank()
                    val latitudValida = latitud.toDoubleOrNull() != null
                    val longitudValida = longitud.toDoubleOrNull() != null

                    if (nombreValido && latitudValida && longitudValida) {
                        onConfirm(nombre, latitud.toDouble(), longitud.toDouble())
                    } else {
                        if (!nombreValido) nombreError = "El nombre es obligatorio"
                        if (!latitudValida) latitudError = "Debe ser un número válido"
                        if (!longitudValida) longitudError = "Debe ser un número válido"
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
