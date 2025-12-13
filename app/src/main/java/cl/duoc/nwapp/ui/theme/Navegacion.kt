// Archivo: ui/theme/Navegacion.kt
package cl.duoc.nwapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember // Import clave para que Compose "recuerde" estados o instancias entre recomposiciones.
import androidx.compose.ui.platform.LocalContext // Proporciona el contexto de la aplicación, necesario para crear la BD.
import androidx.lifecycle.viewmodel.compose.viewModel // El Composable para instanciar ViewModels de forma segura.
import androidx.navigation.compose.NavHost // Es el contenedor que aloja las diferentes pantallas (composable destinations).
import androidx.navigation.compose.composable // Define una "ruta" o pantalla dentro del NavHost.
import androidx.navigation.compose.rememberNavController // Crea y recuerda el controlador de navegación.
import androidx.room.Room // El constructor principal para crear una instancia de la base de datos Room.
import cl.duoc.nwapp.data.remote.RetrofitInstance
import cl.duoc.nwapp.data.repository.AuthRepository
import cl.duoc.nwapp.model.AppDatabase
import cl.duoc.nwapp.repository.DatosRepository
import cl.duoc.nwapp.ui.theme.pages.*
import cl.duoc.nwapp.viewmodel.DatosViewModel
import cl.duoc.nwapp.viewmodel.FormularioViewModel
import cl.duoc.nwapp.viewmodel.SignupViewModel

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
    val datosRepository = remember { DatosRepository(database.datosDao()) }
    val authRepository = remember { AuthRepository(RetrofitInstance.api) }

    // Instancia única del ViewModel que se compartirá entre todas las pantallas que lo necesiten.
    val datosViewModel: DatosViewModel = viewModel(factory = DatosViewModel.Factory(datosRepository))

    NavHost(navController, startDestination = "pagina1") {
        composable("pagina1") { PrimeraPantalla(navController) }

        composable("pagina2") {
            val formularioViewModel: FormularioViewModel = viewModel()
            FormularioCrearCuenta(navController = navController)
        }

        composable("pagina3") { MainScreen(navController, datosViewModel) }

        composable("pagina4") {
            CrearDatos(datosViewModel, navController)
        }

        composable("historial") {
            HistorialScreen(navController, datosViewModel)
        }

        composable("manual") { 
            ManualScreen(navController)
        }

        composable("signup") { 
            val signupViewModel: SignupViewModel = viewModel(factory = SignupViewModel.Factory(authRepository))
            SignupScreen(navController, signupViewModel)
        }
    }
}
