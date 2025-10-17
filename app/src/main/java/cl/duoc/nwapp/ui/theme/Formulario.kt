package cl.duoc.nwapp.ui.theme

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cl.duoc.nwapp.model.FormularioModel
import cl.duoc.nwapp.viewmodel.FormularioViewModel
import cl.duoc.nwapp.R
import androidx.navigation.NavController



@Composable
fun Formulario(viewModel: FormularioViewModel) {

    var abrirModal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.nwa),
            contentDescription = "NWA",
            modifier = Modifier
                .width(250.dp)
                .height(175.dp)
        )

        OutlinedTextField(
            value = viewModel.formulario.nombre,
            onValueChange = { viewModel.formulario.nombre = it },
            label = { Text("Ingresa nombre") },
            isError = !viewModel.verificarNombre(),
            supportingText = { Text( viewModel.mensajesError.nombre, color = androidx.compose.ui.graphics.Color.Red) }
        )
        OutlinedTextField(
            value = viewModel.formulario.correo,
            onValueChange = { viewModel.formulario.correo = it },
            label = { Text("Ingresa correo") },
            isError = !viewModel.verificarCorreo(),
            supportingText = { Text( viewModel.mensajesError.correo, color = androidx.compose.ui.graphics.Color.Red) }
        )
        OutlinedTextField(
            value = viewModel.formulario.edad,
            onValueChange = { viewModel.formulario.edad = it },
            label = { Text("Ingresa edad") },
            isError = !viewModel.verificarEdad(),
            supportingText = { Text( viewModel.mensajesError.edad, color = androidx.compose.ui.graphics.Color.Red) }
        )
        Checkbox(
            checked = viewModel.formulario.terminos,
            onCheckedChange = { viewModel.formulario.terminos = it },
        )
        Text("Acepta los términos")

        Button(
            enabled = viewModel.verificarFormulario(),
            onClick = {
                if(viewModel.verificarFormulario()) {
                    abrirModal = true
                }
            }
        ) {
            Text("Ingresar")
        }

        if (abrirModal) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Confirmación") },
                text = { Text("Usted puede ingresar a la aplicación") },
                confirmButton = {
                    Button(onClick = { abrirModal = false }) { Text("OK") }
                }
            )
        }
        Button(
            onClick = {}
        ) {
            Text("Registrar")
        }

    }
}