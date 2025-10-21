// Archivo: ui/theme/pages/MenuLogin.kt
package cl.duoc.nwapp.ui.theme.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import cl.duoc.nwapp.R

/**
 * Composable para la primera pantalla de la aplicación (pantalla de bienvenida).
 *
 * @param navController El controlador de navegación para moverse a otras pantallas.
 */
@Composable
fun PrimeraPantalla(navController: NavController) {
    // `Column` organiza los elementos verticalmente y los centra en la pantalla.
    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa todo el espacio disponible.
        verticalArrangement = Arrangement.Center, // Centra el contenido verticalmente.
        horizontalAlignment = Alignment.CenterHorizontally, // Centra el contenido horizontalmente.
    ) {
        // Mensaje de bienvenida.
        Text("Bienvenido a la Aplicación NWAPP")

        // Imagen del logo.
        Image(
            painter = painterResource(id = R.drawable.nwa), // Carga la imagen desde los recursos.
            contentDescription = "Logo de la APP",
            contentScale = ContentScale.Crop, // Escala la imagen para que ocupe el espacio sin distorsionarse.
        )

        // Botón que navega a la siguiente pantalla.
        Button(onClick = { 
            // Al hacer clic, se llama a `navigate` con la ruta de la pantalla de destino,
            // que en este caso es el formulario de "logeo" o creación de cuenta.
            navController.navigate("pagina2") 
        }) {
            Text("Logearse")
        }
    }
}
