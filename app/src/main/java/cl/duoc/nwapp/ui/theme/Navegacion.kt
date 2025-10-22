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

    // 1. Usamos `remember` para crear una ÚNICA instancia de nuestras dependencias.
    //    Esto es eficiente y evita que la base de datos se cree en cada recomposición.
    val database = remember {
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database").build()
    }
    val repository = remember { DatosRepository(database.datosDao()) }

    // 2. Creamos una "fábrica" directamente aquí.
    //    Este objeto le dice a `viewModel()` cómo construir un `DatosViewModel`.
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

    NavHost(navController, startDestination = "pagina1") {
        // Pantalla de bienvenida / login.
        composable("pagina1") { PrimeraPantalla(navController) }

        // Pantalla del formulario de creación de cuenta.
        composable("pagina2") {
            val viewModel: FormularioViewModel = viewModel()
            FormularioCrearCuenta(viewModel, navController)
        }
        composable("pagina3") { MainScreen(navController) }

        // 3. Usamos la fábrica para crear el ViewModel.
        //    Ahora la navegación a "pagina4" funcionará sin crashear.
        composable("pagina4") {
            val viewModel2: DatosViewModel = viewModel(factory = datosViewModelFactory)
            CrearDatos(viewModel2, navController)
        }
    }
}
