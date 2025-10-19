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

class BuscadorViewModel(
    private val locationManager: LocationManager,
    context: Context
) : ViewModel() {

    private val geocoder = Geocoder(context)

    var lastKnownLocation by mutableStateOf<Location?>(null)
        private set

    var searchQuery by mutableStateOf("")
    var searchResults by mutableStateOf<List<Address>>(emptyList())
        private set

    init {
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        locationManager.locationFlow()
            .onEach { locationResult: LocationResult ->
                // Solo obtenemos la ubicación una vez para el centrado inicial
                if (lastKnownLocation == null) {
                    lastKnownLocation = locationResult.lastLocation
                }
            }
            .catch { e ->
                println("Error al obtener ubicación: ${'$'}{e.message}")
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
    }

    fun executeSearch() {
        if (searchQuery.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val addresses = geocoder.getFromLocationName(searchQuery, 5) // Máximo 5 resultados
                    searchResults = addresses ?: emptyList()
                } catch (e: IOException) {
                    println("Error en Geocoder: ${'$'}{e.message}")
                    searchResults = emptyList()
                }
            }
        } else {
            searchResults = emptyList()
        }
    }

    fun clearSearchResults() {
        searchResults = emptyList()
    }
}

class BuscadorViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuscadorViewModel::class.java)) {
            val locationManager = LocationManager(context.applicationContext)
            return BuscadorViewModel(locationManager, context.applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
