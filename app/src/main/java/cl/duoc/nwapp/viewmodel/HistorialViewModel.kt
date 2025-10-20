package cl.duoc.nwapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class UbicacionGuardada(
    val nombre: String,
    val latitud: Double,
    val longitud: Double
)

class HistorialViewModel : ViewModel() {
    private val _historial = mutableStateListOf<UbicacionGuardada>()
    val historial: List<UbicacionGuardada> = _historial

    fun addUbicacion(nombre: String, latitudStr: String, longitudStr: String): Boolean {
        val lat = latitudStr.toDoubleOrNull()
        val lon = longitudStr.toDoubleOrNull()
        if (lat != null && lon != null && nombre.isNotBlank()) {
            _historial.add(UbicacionGuardada(nombre, lat, lon))
            return true
        }
        return false
    }
}