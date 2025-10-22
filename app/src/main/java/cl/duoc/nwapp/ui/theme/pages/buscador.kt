
package cl.duoc.nwapp.ui.theme.pages

import android.location.Address

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duoc.nwapp.viewmodel.BuscadorViewModel
import cl.duoc.nwapp.viewmodel.BuscadorViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Buscador(navController: NavController) {
    val context = LocalContext.current
    val factory = BuscadorViewModelFactory(context.applicationContext)
    val viewModel: BuscadorViewModel = viewModel(factory = factory)
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Buscador de Coordenadas") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // --- Barra de Búsqueda ---
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Buscar lugar...") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    IconButton(onClick = {
                        viewModel.executeSearch()
                        focusManager.clearFocus()
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                },
                trailingIcon = {
                    if (viewModel.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.clearSearch() }) {
                            Icon(Icons.Default.Close, contentDescription = "Limpiar")
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.executeSearch()
                    focusManager.clearFocus()
                })
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Lista de Resultados ---
            if (viewModel.searchResults.isNotEmpty()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.searchResults) { address ->
                        SearchResultCard(address = address)
                    }
                }
            } else {
                // Espacio en blanco o mensaje si no hay resultados
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("Introduce una dirección para buscar sus coordenadas.")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            // --- Botones de Navegación ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { navController.navigate("registro") }) {
                    Text("Registrar Manual")
                }
                Button(onClick = { navController.navigate("historial") }) {
                    Text("Historial")
                }
            }
        }
    }
}

@Composable
fun SearchResultCard(address: Address) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = address.getAddressLine(0) ?: "Dirección desconocida",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Lat: ${address.latitude}, Lon: ${address.longitude}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
