@file:Suppress("ktlint:standard:no-wildcard-imports")

package cl.duoc.nwapp.ui.theme.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    val datos by viewModel.datos.collectAsState()
    val nombre by viewModel.nombre.collectAsState()
    val latitud by viewModel.latitud.collectAsState()
    val longitud by viewModel.longitud.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "My TOP",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        Image(
            painter = painterResource(id = R.drawable.nwa),
            contentDescription = "Logo",
            modifier =
                Modifier
                    .width(250.dp)
                    .height(175.dp),
        )

        OutlinedTextField(value = nombre, onValueChange = { viewModel.nombre.value = it }, label = { Text("Nombre") })
        OutlinedTextField(value = latitud, onValueChange = { viewModel.latitud.value = it }, label = { Text("Latitud") })
        OutlinedTextField(value = longitud, onValueChange = { viewModel.longitud.value = it }, label = { Text("Longitud") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.agregarDatos(Datos(nombre = nombre, latitud = latitud, longitud = longitud))
            viewModel.nombre.value = ""
            viewModel.latitud.value = ""
            viewModel.longitud.value = ""
        }) {
            Text("Agregar Datos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(datos) { datos ->
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = datos.nombre,
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = datos.latitud,
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = datos.longitud,
                        modifier = Modifier.weight(1f),
                    )
                }
                Divider()
            }
        }
        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
        }
    }
}
