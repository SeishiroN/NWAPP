// Archivo: ui/theme/Navegacion.kt
package cl.duoc.nwapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.nwapp.ui.theme.pages.FormularioCrearCuenta
import cl.duoc.nwapp.ui.theme.pages.* //
import cl.duoc.nwapp.ui.theme.pages.PrimeraPantalla
import cl.duoc.nwapp.viewmodel.FormularioViewModel



@Composable
fun Navegacion() {

    val navController = rememberNavController()


    NavHost(navController, startDestination = "pagina1") {

        // Pantalla de bienvenida / login.
        composable("pagina1") { PrimeraPantalla(navController) }

        // Pantalla del formulario de creación de cuenta.
        composable("pagina2") {
            // Aquí se crea una instancia del ViewModel específica para esta pantalla.
            val viewModel: FormularioViewModel = viewModel()
            FormularioCrearCuenta(viewModel, navController)
        }

        // Pantalla principal con la barra de búsqueda
        composable("main_screen") { MainScreen(navController) } // <- Registrar la nueva pantalla
    }
}
