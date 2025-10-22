
// Archivo: viewmodel/BuscadorViewModel.kt (Simplificado)
package cl.duoc.nwapp.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel para la pantalla de búsqueda de coordenadas.
 * Su única función es usar Geocoder para convertir un texto en resultados de búsqueda (Address).
 */
class BuscadorViewModel(context: Context) : ViewModel() {

    // Geocoder convierte texto en coordenadas y viceversa.
    private val geocoder = Geocoder(context)

    // El texto actual en la barra de búsqueda.
    var searchQuery by mutableStateOf("")

    // La lista de resultados (direcciones) obtenidos de la búsqueda.
    var searchResults by mutableStateOf<List<Address>>(emptyList())
        private set

    /**
     * Actualiza el estado de `searchQuery` cada vez que el usuario escribe.
     */
    fun onSearchQueryChange(query: String) {
        searchQuery = query
    }

    /**
     * Ejecuta la búsqueda de una ubicación usando el texto de `searchQuery`.
     * Se ejecuta en una corrutina de fondo para no bloquear la UI.
     */
    fun executeSearch() {
        if (searchQuery.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    // geocoder.getFromLocationName realiza la búsqueda.
                    val addresses = geocoder.getFromLocationName(searchQuery, 5) // Pide hasta 5 resultados.
                    searchResults = addresses ?: emptyList()
                } catch (e: IOException) {
                    println("Error en Geocoder: ${e.message}")
                    searchResults = emptyList()
                }
            }
        } else {
            searchResults = emptyList()
        }
    }

    /**
     * Limpia la lista de resultados de búsqueda y el texto de la consulta.
     */
    fun clearSearch() {
        searchQuery = ""
        searchResults = emptyList()
    }
}

/**
 * Factory para crear una instancia de `BuscadorViewModel`.
 * Aunque ahora el ViewModel es más simple, la factory sigue siendo una buena práctica
 * para proveer el `Context` de forma controlada.
 */
class BuscadorViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuscadorViewModel::class.java)) {
            return BuscadorViewModel(context.applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
