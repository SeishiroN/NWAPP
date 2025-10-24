// Archivo: viewmodel/HistorialViewModel.kt
package cl.duoc.nwapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

/**
 * `data class` para representar una ubicación guardada en el historial.
 * Las data classes en Kotlin son ideales para almacenar datos, ya que proveen
 * automáticamente funciones útiles como `equals()`, `hashCode()`, y `toString()`.
 *
 * @property nombre El nombre personalizado del lugar guardado.
 * @property latitud La coordenada de latitud.
 * @property longitud La coordenada de longitud.
 */
data class UbicacionGuardada(
    val nombre: String,
    val latitud: Double,
    val longitud: Double
)

/**
 * ViewModel para gestionar el historial de ubicaciones guardadas.
 * Se encarga de mantener una lista de las ubicaciones que el usuario registra
 * y provee la lógica para añadir nuevas entradas.
 */
class HistorialViewModel : ViewModel() {
    // `_historial` es una lista mutable de `UbicacionGuardada`. Se usa `mutableStateListOf` para que
    // la UI de Compose reaccione automáticamente a los cambios en la lista (adiciones, eliminaciones).
    // Es privada (`_`) para que solo el ViewModel pueda modificarla directamente.
    private val _historial = mutableStateListOf<UbicacionGuardada>()
    
    // `historial` es la versión pública e inmutable de la lista. La UI observa esta lista
    // para mostrar el historial, pero no puede modificarla directamente.
    val historial: List<UbicacionGuardada> = _historial

    /**
     * Añade una nueva ubicación al historial a partir de strings.
     * Intenta convertir la latitud y longitud a `Double`. Si la conversión es exitosa
     * y el nombre no está vacío, crea un objeto `UbicacionGuardada` y lo añade a la lista.
     *
     * @param nombre El nombre del lugar.
     * @param latitudStr La latitud como texto.
     * @param longitudStr La longitud como texto.
     * @return `true` si la ubicación fue añadida con éxito, `false` en caso contrario.
     */
    fun addUbicacion(nombre: String, latitudStr: String, longitudStr: String): Boolean {
        val lat = latitudStr.toDoubleOrNull() // Convierte a Double o retorna null si no es un número válido.
        val lon = longitudStr.toDoubleOrNull()
        if (lat != null && lon != null && nombre.isNotBlank()) {
            _historial.add(UbicacionGuardada(nombre, lat, lon))
            return true // Éxito
        }
        return false // Falla
    }
}