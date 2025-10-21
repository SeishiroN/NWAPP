// Archivo: ui/theme/pages/HistorialScreen.kt
package cl.duoc.nwapp.ui.theme.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.nwapp.viewmodel.HistorialViewModel
import kotlinx.coroutines.launch

/**
 * Composable para la pantalla que muestra el historial de ubicaciones guardadas.
 *
 * @param navController El controlador de navegación para moverse a otras pantallas.
 * @param historialViewModel El ViewModel que contiene la lista de ubicaciones guardadas.
 */
@OptIn(ExperimentalMaterial3Api::class) // Se necesita para usar componentes de Material 3 como Scaffold y TopAppBar.
@Composable
fun HistorialScreen(navController: NavController, historialViewModel: HistorialViewModel) {
    // `rememberDrawerState` crea y recuerda el estado del menú de navegación lateral (abierto/cerrado).
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // `rememberCoroutineScope` nos da un CoroutineScope ligado al ciclo de vida del Composable,
    // necesario para abrir y cerrar el menú de forma asíncrona.
    val scope = rememberCoroutineScope()

    // `ModalNavigationDrawer` es el contenedor principal que provee el menú lateral deslizable.
    ModalNavigationDrawer(
        drawerState = drawerState, // Enlaza el estado que controla si el menú está abierto o cerrado.
        drawerContent = { // El contenido del menú que se desliza.
            ModalDrawerSheet {
                // Cada `NavigationDrawerItem` es una opción en el menú.
                NavigationDrawerItem(
                    label = { Text("Mapa") },
                    selected = false, // `false` porque esta no es la pantalla actual.
                    onClick = {
                        navController.navigate("pagina4") // Navega a la pantalla del mapa.
                        scope.launch { drawerState.close() } // Cierra el menú.
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Historial de busqueda") },
                    selected = true, // `true` porque estamos en esta pantalla.
                    onClick = { scope.launch { drawerState.close() } } // Solo cierra el menú.
                )
                NavigationDrawerItem(
                    label = { Text("Registro de ubicacion") },
                    selected = false,
                    onClick = {
                        navController.navigate("registro") // Navega a la pantalla de registro.
                        scope.launch { drawerState.close() } // Cierra el menú.
                    }
                )
            }
        }
    ) { // El contenido principal de la pantalla, que es visible cuando el menú está cerrado.
        // `Scaffold` es una plantilla de diseño de Material Design. Provee slots para
        // componentes comunes como la barra superior (TopAppBar), botones flotantes, etc.
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Historial de Búsqueda") },
                    navigationIcon = { // El ícono a la izquierda del título, usualmente para abrir el menú.
                        IconButton(onClick = {
                            scope.launch { drawerState.open() } // Abre el menú al hacer clic.
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues -> // El contenido principal de la pantalla se coloca dentro de este lambda.
            // `paddingValues` contiene el padding necesario para no solaparse con la TopAppBar.
            Box(modifier = Modifier.padding(paddingValues)) {
                // Si la lista del historial en el ViewModel está vacía...
                if (historialViewModel.historial.isEmpty()) {
                    // ...muestra un mensaje indicándolo.
                    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        Text("No hay ubicaciones guardadas.")
                    }
                } else {
                    // Si hay elementos, se usa `LazyColumn` para mostrarlos.
                    // `LazyColumn` es una lista que solo renderiza los elementos visibles en pantalla,
                    // lo que es muy eficiente para listas largas.
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        // `items` itera sobre la lista del ViewModel.
                        items(historialViewModel.historial) { ubicacion ->
                            // `Card` es un Composable que crea una tarjeta con elevación.
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth() // Ocupa todo el ancho.
                                    .padding(vertical = 4.dp), // Espaciado vertical entre tarjetas.
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                // El contenido de cada tarjeta.
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(text = ubicacion.nombre, style = MaterialTheme.typography.headlineSmall)
                                    Text(text = "Lat: ${ubicacion.latitud}, Lon: ${ubicacion.longitud}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}