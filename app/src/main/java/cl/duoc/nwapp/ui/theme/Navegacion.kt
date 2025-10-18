package cl.duoc.nwapp.ui.theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.nwapp.R
import cl.duoc.nwapp.ui.theme.pages.*
import cl.duoc.nwapp.viewmodel.FormularioViewModel

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "pagina1") {
        composable("pagina1") { PrimeraPantalla(navController) }
        composable("pagina2") {
            val viewModel: FormularioViewModel = viewModel()
            FormularioCrearCuenta(viewModel, navController)
        }
        composable("pagina3") {
                    val viewModel: FormularioViewModel = viewModel()
                    FormularioCrearCuenta(viewModel, navController)
        }

        // insertar más páginas a futuro, todas en "ui.theme/pages", siguiendo la lógica de las anteriores
    }
}

@Composable
fun Pagina2(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenido a la página 2")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("pagina1") }) {
            Text("Ir a la primera página")
        }
    }
}