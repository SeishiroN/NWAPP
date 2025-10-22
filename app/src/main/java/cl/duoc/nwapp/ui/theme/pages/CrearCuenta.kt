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
    // column para organizar elementos hacia abajo
    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa todo el espacio disponible.
        verticalArrangement = Arrangement.Center, // Centrar verticalmente.
        horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente.
    ) {
        Image(
            painter = painterResource(id = R.drawable.nwa), // para mostrar logo
            contentDescription = "Logo de la app",
            modifier = Modifier.width(250.dp).height(175.dp), // tamaño del logo
        )

        // `OutlinedTextField` caja para ingresar datos
        OutlinedTextField(
            value = viewModel.formulario.nombre, // capta el valor del nombre
            onValueChange = { viewModel.formulario.nombre = it }, // actualiza el valor del nombre.
            label = { Text("Ingresa nombre") }, // Placeholder
            isError = !viewModel.verificarNombre(), // si no va de acuerdo a lo solicitado lanza error
            // `supportingText` ayuda visual en rojo para errores
            supportingText = { Text(viewModel.mensajesError.nombre, color = androidx.compose.ui.graphics.Color.Red) },
        )
        OutlinedTextField(
            value = viewModel.formulario.correo, // capta el valor del correo
            onValueChange = { viewModel.formulario.correo = it }, // actualiza el valor del correo
            label = { Text("Ingresa correo") }, // Placeholder
            isError = !viewModel.verificarCorreo(), // si no va de acuerdo a lo solicitado lanza error
            // `supportingText` ayuda visual en rojo para errores
            supportingText = { Text(viewModel.mensajesError.correo, color = androidx.compose.ui.graphics.Color.Red) },
        )
        OutlinedTextField(
            value = viewModel.formulario.edad,
            onValueChange = { viewModel.formulario.edad = it },
            label = { Text("Ingresa edad") },
            isError = !viewModel.verificarEdad(),
            supportingText = { Text(viewModel.mensajesError.edad, color = androidx.compose.ui.graphics.Color.Red) },
        )

        Checkbox(
            checked = viewModel.formulario.terminos, // registro si marca o no
            onCheckedChange = { viewModel.formulario.terminos = it }, // actualiza si marca o no
        )
        Text("Acepta los términos")

        Button(
            // enabled si cumple con los requisitos de verificar formulario
            enabled = viewModel.verificarFormulario(),
            onClick = {
                // si hace click lanza el modal
                if (viewModel.verificarFormulario()) {
                    abrirModal = true
                }
            },
        ) {
            Text("Ingresar")
        }

        // si modal se abre
        if (abrirModal) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Confirmación") },
                text = { Text("Usted puede ingresar a la aplicación") },
                confirmButton = {
                    Button(onClick = {
                        abrirModal = false
                        navController.navigate("pagina3") {
                            popUpTo("pagina1")
                        }
                    }) { Text("OK") }
                },
            )
        }
    }
}
