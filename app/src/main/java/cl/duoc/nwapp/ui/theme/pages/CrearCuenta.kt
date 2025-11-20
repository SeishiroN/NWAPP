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
import androidx.compose.ui.graphics.Color // Import específico para el color rojo.
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.R // Necesario para acceder a recursos como imágenes (R.drawable.nwa).
import cl.duoc.nwapp.viewmodel.FormularioViewModel

@Composable
fun FormularioCrearCuenta(
    // Se recibe el ViewModel que contiene la lógica y el estado del formulario.
    viewModel: FormularioViewModel,
    // El NavController es esencial para poder navegar a otras pantallas.
    navController: NavController,
) {
    // `remember` guarda el estado de `abrirModal` a través de las recomposiciones.
    // `mutableStateOf(false)` crea un estado observable. Cuando cambia, la UI se actualiza.
    var abrirModal by remember { mutableStateOf(false) }

    // `Column` es un layout que organiza sus elementos hijos de forma vertical (uno debajo del otro).
    Column(
        // `Modifier` se usa para decorar o añadir comportamiento a los Composables.
        modifier = Modifier.fillMaxSize(), // `fillMaxSize` hace que la columna ocupe toda la pantalla.
        // `verticalArrangement` define cómo se distribuyen los hijos verticalmente.
        verticalArrangement = Arrangement.Center, // `Center` los agrupa en el centro del eje vertical.
        // `horizontalAlignment` define cómo se alinean los hijos horizontalmente.
        horizontalAlignment = Alignment.CenterHorizontally, // `CenterHorizontally` los centra en el eje horizontal.
    ) {
        // `Image` es el Composable para mostrar imágenes.
        Image(
            // `painterResource` carga una imagen desde la carpeta de recursos `drawable`.
            painter = painterResource(id = R.drawable.nwa),
            contentDescription = "Logo de la app", // Texto para accesibilidad.
            modifier = Modifier.width(250.dp).height(175.dp), // Define un tamaño fijo para la imagen.
        )

        // `OutlinedTextField` es un campo de texto con un borde, estilo Material Design.
        OutlinedTextField(
            value = viewModel.formulario.nombre, // El valor actual del campo se obtiene del ViewModel.
            // `onValueChange` se ejecuta cada vez que el usuario escribe algo.
            onValueChange = { viewModel.formulario.nombre = it }, // Actualiza el valor en el ViewModel.
            label = { Text("Ingresa nombre") }, // `label` es el texto que aparece dentro del campo (placeholder).
            isError = !viewModel.verificarNombre(), // El campo se marca en rojo si la validación en el ViewModel falla.
            // `supportingText` muestra un texto de ayuda o error debajo del campo.
            supportingText = { Text(viewModel.mensajesError.nombre, color = Color.Red) },
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = viewModel.formulario.correo,
            onValueChange = { viewModel.formulario.correo = it },
            label = { Text("Ingresa correo") },
            isError = !viewModel.verificarCorreo(),
            supportingText = { Text(viewModel.mensajesError.correo, color = Color.Red) },
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = viewModel.formulario.edad,
            onValueChange = { viewModel.formulario.edad = it },
            label = { Text("Ingresa edad") },
            isError = !viewModel.verificarEdad(),
            supportingText = { Text(viewModel.mensajesError.edad, color = Color.Red) },
            textStyle = TextStyle(color = Color.Black)
        )

        // `Checkbox` es una casilla de verificación.
        Checkbox(
            checked = viewModel.formulario.terminos, // El estado (marcado/desmarcado) viene del ViewModel.
            onCheckedChange = { viewModel.formulario.terminos = it }, // Actualiza el estado en el ViewModel.
        )
        Text("Acepta los términos")

        // `Button` es el Composable para un botón estándar.
        Button(
            // `enabled` controla si el botón se puede presionar o no.
            // Se basa en la lógica del ViewModel que comprueba si todo el formulario es válido.
            enabled = viewModel.verificarFormulario(),
            onClick = {
                // La lógica a ejecutar cuando se presiona el botón.
                if (viewModel.verificarFormulario()) {
                    abrirModal = true // Cambia el estado para mostrar el diálogo de confirmación.
                }
            },
        ) {
            Text("Ingresar")
        }

        // Este bloque `if` controla la visibilidad del diálogo de alerta.
        // Como `abrirModal` es un estado observable, Compose muestra el `AlertDialog` cuando es `true`.
        if (abrirModal) {
            AlertDialog(
                onDismissRequest = { }, // Se deja vacío para forzar al usuario a presionar el botón.
                title = { Text("Confirmación") },
                text = { Text("Usted ha ingresado con éxito") },
                confirmButton = {
                    Button(onClick = {
                        abrirModal = false // Oculta el modal.
                        // `navController.navigate` es el comando para cambiar de pantalla.
                        navController.navigate("pagina3") {
                            // `popUpTo` limpia el historial de navegación hasta `pagina1`.
                            // Evita que el usuario pueda volver a la pantalla de registro con el botón "Atrás".
                            popUpTo("pagina1")
                        }
                    }) { Text("OK") }
                },
            )
        }
    }
}
