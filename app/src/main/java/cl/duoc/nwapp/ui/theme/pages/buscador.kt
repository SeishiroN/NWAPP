// Archivo: ui/theme/pages/buscador.kt
package cl.duoc.nwapp.ui.theme.pages

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.duoc.nwapp.viewmodel.BuscadorViewModel
import cl.duoc.nwapp.viewmodel.BuscadorViewModelFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun Buscador(navController: NavController) {
    val context = LocalContext.current
    val factory = BuscadorViewModelFactory(context.applicationContext)
    val viewModel: BuscadorViewModel = viewModel(factory = factory)
    val focusManager = LocalFocusManager.current

    var hasLocationPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted -> hasLocationPermission = isGranted }
    )

    LaunchedEffect(key1 = true) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    if (hasLocationPermission) {
        MapScreen(viewModel = viewModel) {
            focusManager.clearFocus() // Oculta el teclado
        }
    } else {
        PermissionRequestScreen {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}

@Composable
fun MapScreen(viewModel: BuscadorViewModel, onMapClick: () -> Unit) {
    val location = viewModel.lastKnownLocation
    val cameraPositionState = rememberCameraPositionState()
    var searchedLocationMarkerState by remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(location) {
        if (location != null) {
            val latLng = LatLng(location.latitude, location.longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (location != null) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { onMapClick() }
            ) {
                // Marcador de ubicación actual (azul)
                Marker(
                    state = MarkerState(position = LatLng(location.latitude, location.longitude)),
                    title = "Mi Ubicación",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                )
                // Marcador de ubicación buscada (rojo)
                searchedLocationMarkerState?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = viewModel.searchQuery
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Obteniendo ubicación para mostrar el mapa...")
            }
        }

        // UI de búsqueda superpuesta
        SearchUI(viewModel = viewModel, onSearchResultClick = {
            val newPos = LatLng(it.latitude, it.longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(newPos, 15f)
            searchedLocationMarkerState = newPos
            onMapClick()
        })
    }
}

@Composable
fun SearchUI(viewModel: BuscadorViewModel, onSearchResultClick: (Address) -> Unit) {
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text("Buscar lugar...") },
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (viewModel.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
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

        if (viewModel.searchResults.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            ) {
                items(viewModel.searchResults) { address ->
                    Text(
                        text = address.getAddressLine(0) ?: "Lugar sin nombre",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { 
                                onSearchResultClick(address)
                                viewModel.clearSearchResults()
                             }
                            .padding(16.dp)
                    )
                    Divider()
                }
            }
        }
    }
}


@Composable
fun PermissionRequestScreen(onRequest: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Se necesita permiso de ubicación para mostrar el mapa.")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRequest) {
            Text("Conceder Permiso")
        }
    }
}
