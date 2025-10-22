// Archivo: ui/theme/Navegacion.kt
package cl.duoc.nwapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import cl.duoc.nwapp.model.AppDatabase
import cl.duoc.nwapp.repository.DatosRepository
import cl.duoc.nwapp.ui.theme.pages.*
import cl.duoc.nwapp.viewmodel.DatosViewModel
import cl.duoc.nwapp.viewmodel.FormularioViewModel

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // La instancia única de la base de datos y el repositorio.
    val database = remember {
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database").build()
    }
    val repository = remember { DatosRepository(database.datosDao()) }

    // La fábrica para nuestro DatosViewModel.
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

    // Creamos una única instancia del ViewModel para compartirla entre pantallas.
    val datosViewModel: DatosViewModel = viewModel(factory = datosViewModelFactory)

    NavHost(navController, startDestination = "pagina1") {
        composable("pagina1") { PrimeraPantalla(navController) }

        composable("pagina2") {
            val formularioViewModel: FormularioViewModel = viewModel()
            FormularioCrearCuenta(formularioViewModel, navController)
        }

        // Le pasamos el mismo ViewModel a la MainScreen.
        composable("pagina3") { MainScreen(navController, datosViewModel) }

        // Y también a la pantalla de creación.
        composable("pagina4") {
            CrearDatos(datosViewModel, navController)
        }
    }
}
