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

@Composable
fun PrimeraPantalla(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Bienvenido a la Aplicación NWAPP")

        Image(
            painter = painterResource(id = R.drawable.nwa),
            contentDescription = "Imagen de un gato",
            contentScale = ContentScale.Crop,
        )
        Button(onClick = { navController.navigate("pagina2") }) {
            Text("Registrarse")
        }
    }
}
