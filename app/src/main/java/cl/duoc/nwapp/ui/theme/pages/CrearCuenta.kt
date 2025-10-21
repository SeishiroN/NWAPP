// Archivo: ui/theme/pages/CrearCuenta.kt
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

/**
 * Composable que define la pantalla del formulario de creación de cuenta.
 *
 * @param viewModel La instancia del ViewModel que gestiona el estado y la lógica de este formulario.
 * @param navController El controlador de navegación para moverse a otras pantallas.
 */
@Composable
fun FormularioCrearCuenta(
    viewModel: FormularioViewModel,
    navController: NavController,
) {
    // `abrirModal` es un estado local que controla la visibilidad del diálogo de confirmación.
    // `remember` se usa para que el estado (true/false) sobreviva a las recomposiciones.
    var abrirModal by remember { mutableStateOf(false) }

    // `Column` es un Composable que organiza a sus hijos en una secuencia vertical.
    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa todo el espacio disponible.
        verticalArrangement = Arrangement.Center, // Centra a sus hijos verticalmente.
        horizontalAlignment = Alignment.CenterHorizontally, // Centra a sus hijos horizontalmente.
    ) {
        // Muestra el logo de la aplicación.
        Image(
            painter = painterResource(id = R.drawable.nwa), // Carga la imagen desde los recursos.
            contentDescription = "Logo de la app",
            modifier = Modifier.width(250.dp).height(175.dp) // Define un tamaño fijo.
        )

        // `OutlinedTextField` es un campo de texto con un borde.
        OutlinedTextField(
            value = viewModel.formulario.nombre, // El valor del campo se enlaza al estado en el ViewModel.
            onValueChange = { viewModel.formulario.nombre = it }, // Cuando el usuario escribe, se actualiza el ViewModel.
            label = { Text("Ingresa nombre") }, // Etiqueta que se muestra sobre el campo.
            isError = !viewModel.verificarNombre(), // El campo se marca como error si la validación falla.
            // `supportingText` muestra un texto de ayuda o de error debajo del campo.
            supportingText = { Text(viewModel.mensajesError.nombre, color = androidx.compose.ui.graphics.Color.Red) },
        )
        OutlinedTextField(
            value = viewModel.formulario.correo,
            onValueChange = { viewModel.formulario.correo = it },
            label = { Text("Ingresa correo") },
            isError = !viewModel.verificarCorreo(),
            supportingText = { Text(viewModel.mensajesError.correo, color = androidx.compose.ui.graphics.Color.Red) },
        )
        OutlinedTextField(
            value = viewModel.formulario.edad,
            onValueChange = { viewModel.formulario.edad = it },
            label = { Text("Ingresa edad") },
            isError = !viewModel.verificarEdad(),
            supportingText = { Text(viewModel.mensajesError.edad, color = androidx.compose.ui.graphics.Color.Red) },
        )
        
        // `Checkbox` para aceptar los términos y condiciones.
        Checkbox(
            checked = viewModel.formulario.terminos, // El estado (marcado/desmarcado) se enlaza al ViewModel.
            onCheckedChange = { viewModel.formulario.terminos = it }, // Actualiza el ViewModel al hacer clic.
        )
        Text("Acepta los términos")

        // Botón para enviar el formulario.
        Button(
            // El botón solo está habilitado (`enabled`) si el formulario completo es válido.
            enabled = viewModel.verificarFormulario(),
            onClick = {
                // Al hacer clic, si el formulario es válido, se muestra el diálogo de confirmación.
                if (viewModel.verificarFormulario()) {
                    abrirModal = true
                }
            },
        ) {
            Text("Ingresar")
        }

        // Este bloque `if` muestra el diálogo solo si `abrirModal` es `true`.
        if (abrirModal) {
            AlertDialog(
                onDismissRequest = { }, // No se puede cerrar tocando fuera.
                title = { Text("Confirmación") },
                text = { Text("Usted puede ingresar a la aplicación") },
                confirmButton = {
                    // Botón "OK" del diálogo.
                    Button(onClick = { 
                        abrirModal = false // Cierra el diálogo.
                        navController.navigate("pagina4") // Navega a la pantalla del mapa.
                    }) { Text("OK") }
                },
            )
        }
    }
}
