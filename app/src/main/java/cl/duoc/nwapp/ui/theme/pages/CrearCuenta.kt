
package cl.duoc.nwapp.ui.theme.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duoc.nwapp.R
import cl.duoc.nwapp.viewmodel.FormularioViewModel

/**
 * --- PANTALLA DE LOGIN DEFINITIVA ---
 * Corregida para usar el nuevo FormularioViewModel con email y password.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioCrearCuenta(
    viewModel: FormularioViewModel = viewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Login") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.nwa),
                contentDescription = "Logo de la APP",
                modifier = Modifier.height(150.dp)
            )
            Spacer(Modifier.height(24.dp))

            // --- FIX: Campo Email conectado al nuevo ViewModel ---
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                isError = viewModel.errorMessage.isNotEmpty(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            // --- FIX: Campo Contraseña conectado al nuevo ViewModel ---
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                isError = viewModel.errorMessage.isNotEmpty(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.errorMessage.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = viewModel.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(Modifier.height(24.dp))

            // --- FIX: Botón conectado a la función de login de la API ---
            Button(
                onClick = {
                    viewModel.realizarLogin {
                        // Navega al mapa SÓLO si el login es exitoso.
                        navController.navigate("pagina3") {
                            popUpTo("pagina2") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }
        }
    }
}
