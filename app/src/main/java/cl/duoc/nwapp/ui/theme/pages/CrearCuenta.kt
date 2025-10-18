package cl.duoc.nwapp.ui.theme.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.R
import cl.duoc.nwapp.viewmodel.FormularioViewModel

@Composable
fun FormularioCrearCuenta(
    viewModel: FormularioViewModel,
    navController: NavController,
) {
    var abrirModal by remember { mutableStateOf(false) }

    val nombre by viewModel.nombre.collectAsState()
    val correo by viewModel.correo.collectAsState()
    val edad by viewModel.edad.collectAsState()
    val terminos by viewModel.terminos.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.nwa),
            contentDescription = "NWA",
            modifier =
                Modifier
                    .width(250.dp)
                    .height(175.dp),
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { viewModel.nombre.value = it },
            label = { Text("Ingresa nombre") },
            isError = !viewModel.verificarNombre(),
            supportingText = { Text(viewModel.mensajesError.nombre, color = androidx.compose.ui.graphics.Color.Red) },
        )
        OutlinedTextField(
            value = correo,
            onValueChange = { viewModel.correo.value = it },
            label = { Text("Ingresa correo") },
            isError = !viewModel.verificarCorreo(),
            supportingText = { Text(viewModel.mensajesError.correo, color = androidx.compose.ui.graphics.Color.Red) },
        )
        OutlinedTextField(
            value = edad,
            onValueChange = { viewModel.edad.value = it },
            label = { Text("Ingresa edad") },
            isError = !viewModel.verificarEdad(),
            supportingText = { Text(viewModel.mensajesError.edad, color = androidx.compose.ui.graphics.Color.Red) },
        )
        Checkbox(
            checked = terminos,
            onCheckedChange = { viewModel.terminos.value = it },
        )
        Text("Acepta los términos")

        Button(
            enabled = viewModel.verificarFormulario(),
            onClick = {
                if (viewModel.verificarFormulario()){
                    viewModel.login()
                }
                }
            ,
        ) {
            Text("Ingresar")
        }

        if (viewModel.respuesta.value) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Confirmación") },
                text = { Text("Usted puede ingresar a la aplicación") },
                confirmButton = {
                    Button(onClick = { viewModel.respuesta.value = false }) { Text("OK") }
                },
            )
        }

        Button(onClick = { navController.navigate("pagina3") }) {
            Text("dsklakdsaj")
        }


    }
}
