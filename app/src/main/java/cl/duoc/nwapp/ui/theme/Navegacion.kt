package cl.duoc.nwapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.duoc.nwapp.ui.theme.pages.*
import cl.duoc.nwapp.viewmodel.FormularioCreacionUserViewModel
import cl.duoc.nwapp.viewmodel.FormularioViewModel
import cl.duoc.nwapp.viewmodel.HistorialViewModel

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    val historialViewModel: HistorialViewModel = viewModel()

    NavHost(navController, startDestination = "pagina1") {
        composable("pagina1") { PrimeraPantalla(navController) }
        composable("pagina2") {
            val viewModel: FormularioViewModel = viewModel()
            FormularioCrearCuenta(viewModel, navController)
        }
        composable("pagina3") {
            val viewModel: FormularioCreacionUserViewModel = viewModel()
        }
        composable("pagina4") {
            Buscador(navController)
        }
        composable(
            route = "registro?lat={lat}&lon={lon}&nombre={nombre}",
            arguments = listOf(
                navArgument("lat") { type = NavType.FloatType; defaultValue = 0f },
                navArgument("lon") { type = NavType.FloatType; defaultValue = 0f },
                navArgument("nombre") { type = NavType.StringType; defaultValue = "" }
            )
        ) {
            val lat = it.arguments?.getFloat("lat")?.toString() ?: ""
            val lon = it.arguments?.getFloat("lon")?.toString() ?: ""
            val nombre = it.arguments?.getString("nombre") ?: ""
            RegistroUbicacionScreen(navController, historialViewModel, nombre, lat, lon)
        }
        composable("historial") {
            HistorialScreen(navController, historialViewModel)
        }
        // insertar más páginas a futuro, todas en "ui.theme/pages", siguiendo la lógica de las anteriores
    }
}
