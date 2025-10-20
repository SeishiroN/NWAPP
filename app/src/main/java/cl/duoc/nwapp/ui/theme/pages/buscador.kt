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
import androidx.compose.material.icons.filled.Menu
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
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Buscador(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text("Mapa") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Historial de busqueda") },
                    selected = false,
                    onClick = {
                        navController.navigate("historial")
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Registro de ubicacion") },
                    selected = false,
                    onClick = {
                        navController.navigate("registro")
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Buscador") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
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

            Box(modifier = Modifier.padding(paddingValues)) {
                if (hasLocationPermission) {
                    MapScreen(viewModel = viewModel, navController = navController) {
                        focusManager.clearFocus() // Oculta el teclado
                    }
                } else {
                    PermissionRequestScreen {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
            }
        }
    }
}

@Composable
fun MapScreen(viewModel: BuscadorViewModel, navController: NavController, onMapClick: () -> Unit) {
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
        SearchUI(viewModel = viewModel, navController = navController, onSearchResultClick = {
            val newPos = LatLng(it.latitude, it.longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(newPos, 15f)
            searchedLocationMarkerState = newPos
            onMapClick()
        })
    }
}

@Composable
fun SearchUI(viewModel: BuscadorViewModel, navController: NavController, onSearchResultClick: (Address) -> Unit) {
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
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = address.getAddressLine(0) ?: "Lugar sin nombre",
                            modifier = Modifier.weight(1f).clickable { onSearchResultClick(address) }
                        )
                        Button(onClick = { 
                            val lat = address.latitude
                            val lon = address.longitude
                            val name = URLEncoder.encode(address.getAddressLine(0) ?: "Lugar sin nombre", StandardCharsets.UTF_8.name())
                            navController.navigate("registro?lat=$lat&lon=$lon&nombre=$name")
                            viewModel.clearSearchResults()
                        }) {
                            Text("Registrar")
                        }
                    }
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