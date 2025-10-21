// Archivo: viewmodel/BuscadorViewModel.kt
package cl.duoc.nwapp.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cl.duoc.nwapp.util.LocationManager
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel para la pantalla del buscador y el mapa.
 *
 * Este ViewModel gestiona el estado relacionado con la ubicación del usuario,
 * la búsqueda de lugares y los resultados de esa búsqueda.
 *
 * @property locationManager Un gestor para obtener actualizaciones de la ubicación del dispositivo.
 * @property context El contexto de la aplicación, necesario para inicializar Geocoder.
 */
class BuscadorViewModel(
    private val locationManager: LocationManager,
    context: Context
) : ViewModel() {

    // Geocoder es una clase de Android que permite convertir coordenadas (lat, lon)
    // a direcciones postales y viceversa (geocodificación y geocodificación inversa).
    private val geocoder = Geocoder(context)

    // Almacena la última ubicación conocida del dispositivo. Es un estado mutable para
    // que el mapa en la UI se actualice cuando se obtenga la ubicación.
    // El 'private set' significa que solo puede ser modificado desde dentro de este ViewModel.
    var lastKnownLocation by mutableStateOf<Location?>(null)
        private set

    // El texto actual en la barra de búsqueda.
    var searchQuery by mutableStateOf("")

    // La lista de resultados (direcciones) obtenidos de la búsqueda.
    // Es 'private set' para proteger su modificación desde fuera.
    var searchResults by mutableStateOf<List<Address>>(emptyList())
        private set

    // El bloque 'init' se ejecuta cuando se crea una instancia del ViewModel por primera vez.
    init {
        startLocationUpdates()
    }

    /**
     * Inicia la recepción de actualizaciones de la ubicación del dispositivo.
     * Utiliza el Flow proporcionado por `locationManager`.
     */
    private fun startLocationUpdates() {
        locationManager.locationFlow()
            .onEach { locationResult: LocationResult ->
                // onEach se ejecuta cada vez que el Flow emite un nuevo valor.
                // Aquí, solo nos interesa la primera ubicación para centrar el mapa inicialmente.
                if (lastKnownLocation == null) {
                    lastKnownLocation = locationResult.lastLocation
                }
            }
            .catch { e ->
                // catch maneja cualquier error que ocurra en el Flow.
                println("Error al obtener ubicación: ${e.message}")
            }
            // launchIn inicia la corrutina en el scope del ViewModel, asegurando que se cancele
            // automáticamente cuando el ViewModel sea destruido.
            .launchIn(viewModelScope)
    }

    /**
     * Actualiza el estado de `searchQuery` cada vez que el usuario escribe en la barra de búsqueda.
     */
    fun onSearchQueryChange(query: String) {
        searchQuery = query
    }

    /**
     * Ejecuta la búsqueda de una ubicación usando el texto de `searchQuery`.
     * Esta operación puede tardar, por lo que se ejecuta en una corrutina de fondo (Dispatchers.IO).
     */
    fun executeSearch() {
        if (searchQuery.isNotBlank()) {
            // viewModelScope.launch inicia una corrutina que vive mientras el ViewModel exista.
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    // geocoder.getFromLocationName realiza la búsqueda.
                    // El segundo parámetro (5) es el número máximo de resultados a devolver.
                    val addresses = geocoder.getFromLocationName(searchQuery, 5)
                    searchResults = addresses ?: emptyList() // Asigna los resultados, o una lista vacía si es null.
                } catch (e: IOException) {
                    // Maneja errores de red o del servicio Geocoder.
                    println("Error en Geocoder: ${e.message}")
                    searchResults = emptyList()
                }
            }
        } else {
            // Si la búsqueda está vacía, limpia los resultados.
            searchResults = emptyList()
        }
    }

    /**
     * Limpia la lista de resultados de búsqueda.
     */
    fun clearSearchResults() {
        searchResults = emptyList()
    }
}

/**
 * Factory para crear una instancia de `BuscadorViewModel`.
 *
 * Es necesaria porque `BuscadorViewModel` tiene dependencias en su constructor (`LocationManager` y `Context`).
 * El sistema de Android no sabe cómo proveer estas dependencias por sí mismo, por lo que
 * esta Factory le indica cómo construir el ViewModel.
 */
class BuscadorViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Verifica si la clase que se solicita es BuscadorViewModel.
        if (modelClass.isAssignableFrom(BuscadorViewModel::class.java)) {
            // Si lo es, crea y retorna una nueva instancia con sus dependencias.
            val locationManager = LocationManager(context.applicationContext)
            return BuscadorViewModel(locationManager, context.applicationContext) as T
        }
        // Si se pide crear un ViewModel desconocido, lanza una excepción.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
