@file:Suppress("ktlint:standard:no-wildcard-imports")

package cl.duoc.nwapp.ui.theme.pages

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
    val datos by viewModel.datos.collectAsState()
    val nombre by viewModel.nombre.collectAsState()
    val latitud by viewModel.latitud.collectAsState()
    val longitud by viewModel.longitud.collectAsState()

    // Estados para los errores de validación.
    val nombreError by viewModel.nombreError.collectAsState()
    val latitudError by viewModel.latitudError.collectAsState()
    val longitudError by viewModel.longitudError.collectAsState()

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
            value = nombre,
            onValueChange = {
                viewModel.nombre.value = it
                viewModel.validarNombre() // Valida en tiempo real.
            },
            label = { Text("Nombre") },
            singleLine = true,
            isError = nombreError.isNotEmpty(),
            supportingText = { if (nombreError.isNotEmpty()) Text(nombreError, color = Color.Red) },
            textStyle = TextStyle(color = Color.Black)
        )
        OutlinedTextField(
            value = latitud,
            onValueChange = {
                viewModel.latitud.value = it.replace(',', '.')
                viewModel.validarLatitud() // Valida en tiempo real.
            },
            label = { Text("Latitud") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = latitudError.isNotEmpty(),
            supportingText = { if (latitudError.isNotEmpty()) Text(latitudError, color = Color.Red) },
            textStyle = TextStyle(color = Color.Black)
        )
        OutlinedTextField(
            value = longitud,
            onValueChange = {
                viewModel.longitud.value = it.replace(',', '.')
                viewModel.validarLongitud() // Valida en tiempo real.
            },
            label = { Text("Longitud") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = longitudError.isNotEmpty(),
            supportingText = { if (longitudError.isNotEmpty()) Text(longitudError, color = Color.Red) },
            textStyle = TextStyle(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                if (viewModel.validarCampos()) {
                    viewModel.agregarDatos(Datos(nombre = nombre, latitud = latitud, longitud = longitud))
                    // Se limpia el formulario silenciosamente tras el éxito.
                    viewModel.nombre.value = ""
                    viewModel.latitud.value = ""
                    viewModel.longitud.value = ""
                }
            }) {
                Text("Agregar Datos")
            }

            Button(onClick = {
                datos.firstOrNull()?.let { ultimoDato ->
                    viewModel.eliminarDatos(ultimoDato)
                }
            }) {
                Text("Borrar último dato")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(datos) { dato ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = dato.nombre, modifier = Modifier.weight(1f))
                    Text(text = dato.latitud, modifier = Modifier.weight(1f))
                    Text(text = dato.longitud, modifier = Modifier.weight(1f))
                }
                Divider()
            }
        }
        Button(onClick = { navController.popBackStack() }) { 
            Text("Regresar")
        }
    }
}
