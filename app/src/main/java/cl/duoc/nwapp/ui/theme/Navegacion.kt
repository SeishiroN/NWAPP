// Archivo: ui/theme/Navegacion.kt
package cl.duoc.nwapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember // Import clave para que Compose "recuerde" estados o instancias entre recomposiciones.
import androidx.compose.ui.platform.LocalContext // Proporciona el contexto de la aplicación, necesario para crear la BD.
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel // El Composable para instanciar ViewModels de forma segura.
import androidx.navigation.compose.NavHost // Es el contenedor que aloja las diferentes pantallas (composable destinations).
import androidx.navigation.compose.composable // Define una "ruta" o pantalla dentro del NavHost.
import androidx.navigation.compose.rememberNavController // Crea y recuerda el controlador de navegación.
import androidx.room.Room // El constructor principal para crear una instancia de la base de datos Room.
import cl.duoc.nwapp.model.AppDatabase
import cl.duoc.nwapp.repository.DatosRepository
import cl.duoc.nwapp.ui.theme.pages.* // Importa todas las pantallas que hemos creado.
import cl.duoc.nwapp.viewmodel.DatosViewModel
import cl.duoc.nwapp.viewmodel.FormularioViewModel

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // -- INYECCIÓN DE DEPENDENCIAS MANUAL --
    val database = remember {
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    val repository = remember { DatosRepository(database.datosDao()) }
    val datosViewModelFactory = remember(repository) {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(DatosViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return DatosViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    // Instancia única del ViewModel que se compartirá entre todas las pantallas que lo necesiten.
    val datosViewModel: DatosViewModel = viewModel(factory = datosViewModelFactory)

    NavHost(navController, startDestination = "pagina1") {
        composable("pagina1") { PrimeraPantalla(navController) }

        composable("pagina2") {
            val formularioViewModel: FormularioViewModel = viewModel()
            FormularioCrearCuenta(formularioViewModel, navController)
        }

        composable("pagina3") { MainScreen(navController, datosViewModel) }

        composable("pagina4") {
            CrearDatos(datosViewModel, navController)
        }

        // Ruta para la nueva pantalla de Historial.
        // Le pasamos la MISMA instancia de `datosViewModel` que usan las otras pantallas.
        composable("historial") {
            HistorialScreen(navController, datosViewModel)
        }
    }
}
