@file:Suppress("ktlint:standard:no-wildcard-imports")

package cl.duoc.nwapp.ui.theme.pages

import android.widget.Toast // Import para mostrar mensajes emergentes (Toasts).
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // Componente para mostrar listas largas de forma eficiente.
import androidx.compose.foundation.lazy.items // Extensión de LazyColumn para procesar una lista de datos.
import androidx.compose.foundation.text.KeyboardOptions // Para personalizar el teclado (ej: numérico).
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType // Define el tipo de teclado a mostrar.
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.R
import cl.duoc.nwapp.model.Datos
import cl.duoc.nwapp.viewmodel.DatosViewModel

@Composable
fun CrearDatos(
    viewModel: DatosViewModel,
    navController: NavController,
) {
    // --- RECOLECCIÓN DE ESTADOS DEL VIEWMODEL ---
    // La UI se suscribe a los StateFlows del ViewModel. `collectAsState` asegura que la UI
    // se "recomponga" (actualice) automáticamente cada vez que uno de estos valores cambia.
    val datos by viewModel.datos.collectAsState() // Estado de la lista de ubicaciones.
    val nombre by viewModel.nombre.collectAsState() // Estado del campo de texto "nombre".
    val latitud by viewModel.latitud.collectAsState() // Estado del campo de texto "latitud".
    val longitud by viewModel.longitud.collectAsState() // Estado del campo de texto "longitud".

    // Estados para los errores de validación. La UI los usará para mostrar mensajes en rojo.
    val nombreError by viewModel.nombreError.collectAsState()
    val latitudError by viewModel.latitudError.collectAsState()
    val longitudError by viewModel.longitudError.collectAsState()

    val context = LocalContext.current // Contexto necesario para crear Toasts.

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.nwa),
            contentDescription = "Logo",
            modifier = Modifier.width(250.dp).height(175.dp).padding(bottom = 16.dp),
        )

        // --- FORMULARIO DE INGRESO DE DATOS ---
        OutlinedTextField(
            value = nombre, // El valor del campo viene del ViewModel.
            onValueChange = { viewModel.nombre.value = it }, // Cualquier cambio actualiza el ViewModel.
            label = { Text("Nombre") },
            singleLine = true, // Evita que el texto salte a una nueva línea.
            isError = nombreError.isNotEmpty(), // El campo se marca en rojo si hay un mensaje de error.
            // Muestra el texto de error debajo del campo si no está vacío.
            supportingText = { if (nombreError.isNotEmpty()) Text(nombreError, color = Color.Red) }
        )
        OutlinedTextField(
            value = latitud,
            onValueChange = { viewModel.latitud.value = it.replace(',', '.') }, // Reemplaza comas por puntos al escribir.
            label = { Text("Latitud") },
            // Muestra un teclado numérico que acepta decimales y signos.
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = latitudError.isNotEmpty(),
            supportingText = { if (latitudError.isNotEmpty()) Text(latitudError, color = Color.Red) }
        )
        OutlinedTextField(
            value = longitud,
            onValueChange = { viewModel.longitud.value = it.replace(',', '.') },
            label = { Text("Longitud") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = longitudError.isNotEmpty(),
            supportingText = { if (longitudError.isNotEmpty()) Text(longitudError, color = Color.Red) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround // Distribuye los botones con espacio entre ellos.
        ) {
            Button(onClick = {
                // La lógica se delega al ViewModel. La UI solo pregunta si los campos son válidos.
                if (viewModel.validarCampos()) {
                    // Si es válido, se crea el objeto `Datos` y se llama a la función del ViewModel.
                    viewModel.agregarDatos(Datos(nombre = nombre, latitud = latitud, longitud = longitud))
                    Toast.makeText(context, "Dato agregado con éxito", Toast.LENGTH_SHORT).show()

                    // Limpiar los campos después de un ingreso exitoso.
                    viewModel.nombre.value = ""
                    viewModel.latitud.value = ""
                    viewModel.longitud.value = ""
                }
            }) {
                Text("Agregar Datos")
            }

            Button(onClick = {
                // La lista `datos` está ordenada por ID descendente (ver DAO).
                // `firstOrNull()` obtiene el primer elemento (el último agregado) de forma segura.
                datos.firstOrNull()?.let { ultimoDato ->
                    viewModel.eliminarDatos(ultimoDato) // Llama a la función de eliminar del ViewModel.
                }
            }) {
                Text("Borrar último dato")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- LISTA DE DATOS REGISTRADOS ---
        // `LazyColumn` es la versión de Compose de `RecyclerView`. Solo renderiza los elementos
        // que son visibles en pantalla, haciéndola muy eficiente para listas largas.
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp), // Espacio entre cada ítem.
        ) {
            // `items(datos)` le dice a la LazyColumn que use la lista `datos` como fuente.
            items(datos) { dato -> // `dato` es cada elemento individual de la lista.
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = dato.nombre, modifier = Modifier.weight(1f)) // `weight(1f)` hace que cada Text ocupe un espacio equitativo.
                    Text(text = dato.latitud, modifier = Modifier.weight(1f))
                    Text(text = dato.longitud, modifier = Modifier.weight(1f))
                }
                Divider() // Una línea divisoria debajo de cada fila.
            }
        }
        Button(onClick = { navController.popBackStack() }) { // `popBackStack` navega a la pantalla anterior.
            Text("Regresar")
        }
    }
}
