package cl.duoc.nwapp.ui.theme.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ManualScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Manual de Usuario",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("1) Usted puede mover el mapa de acuerdo a lo que necesite")
            Text("2) En el \"Registrar ubicación\" deberá rellenar los campos solicitados, si lo hace y apreta agregar datos, se guardarán y podrá ver que se creó un marcador en el mapa, si desea puede borrar el último dato ingresado.")
            Text("3) si usted va a \"Ver Historal\" verá el historial total de las ubicaciones ingresadas.")
            Text("4) Si apreta en \"Regresar al Login\" podrá volver al inicio de la aplicación.")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Volver")
        }
    }
}
