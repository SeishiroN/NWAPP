// Archivo: ui/theme/Navegacion.kt
package cl.duoc.nwapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.duoc.nwapp.ui.theme.pages.*
import cl.duoc.nwapp.viewmodel.FormularioViewModel
import cl.duoc.nwapp.viewmodel.HistorialViewModel

/**
 * Este Composable define el grafo de navegación de la aplicación.
 * Utiliza Jetpack Navigation para Compose para gestionar las transiciones entre pantallas.
 */
@Composable
fun Navegacion() {
    // `rememberNavController()` crea y recuerda un NavController. Este controlador es el
    // cerebro de la navegación: gestiona la pila de pantallas (back stack) y nos permite
    // navegar entre ellas con `navController.navigate("ruta")`.
    val navController = rememberNavController()

    // Se obtiene una instancia de HistorialViewModel. `viewModel()` es un helper de Compose
    // que provee una instancia del ViewModel asociada al ciclo de vida correcto. Esto asegura
    // que el ViewModel sobreviva a recomposiciones y cambios de configuración.
    // Este ViewModel se compartirá entre las pantallas que lo necesiten (Registro y Historial).
    val historialViewModel: HistorialViewModel = viewModel()

    // `NavHost` es el contenedor que aloja las diferentes pantallas (destinos) de la app.
    // `navController`: El controlador que gestionará este NavHost.
    // `startDestination`: La ruta de la pantalla que se mostrará al iniciar la app.
    NavHost(navController, startDestination = "pagina1") {
        
        // `composable("ruta")` define una pantalla en el grafo de navegación.
        // La ruta es un String único que identifica a la pantalla.

        // Pantalla de bienvenida / login.
        composable("pagina1") { PrimeraPantalla(navController) }
        
        // Pantalla del formulario de creación de cuenta.
        composable("pagina2") {
            // Aquí se crea una instancia del ViewModel específica para esta pantalla.
            val viewModel: FormularioViewModel = viewModel()
            FormularioCrearCuenta(viewModel, navController)
        }

        // Pantalla principal con el mapa y el buscador.
        composable("pagina4") {
            Buscador(navController)
        }

        // Pantalla para registrar una nueva ubicación. Esta ruta es más compleja porque
        // define argumentos opcionales para recibir datos de otra pantalla (el mapa).
        composable(
            route = "registro?lat={lat}&lon={lon}&nombre={nombre}", // La sintaxis `?arg={arg}` define args opcionales.
            arguments = listOf(
                // Se define cada argumento, su tipo y un valor por defecto.
                navArgument("lat") { type = NavType.FloatType; defaultValue = 0f },
                navArgument("lon") { type = NavType.FloatType; defaultValue = 0f },
                navArgument("nombre") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry -> // El lambda nos da acceso al `backStackEntry` para leer los argumentos.
            // Se extraen los valores de los argumentos. Si no se pasan, se usan los valores por defecto.
            val lat = backStackEntry.arguments?.getFloat("lat")?.toString() ?: ""
            val lon = backStackEntry.arguments?.getFloat("lon")?.toString() ?: ""
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            
            // Se llama al Composable de la pantalla, pasándole los datos extraídos.
            RegistroUbicacionScreen(navController, historialViewModel, nombre, lat, lon)
        }

        // Pantalla que muestra el historial de ubicaciones guardadas.
        composable("historial") {
            HistorialScreen(navController, historialViewModel)
        }
    }
}
