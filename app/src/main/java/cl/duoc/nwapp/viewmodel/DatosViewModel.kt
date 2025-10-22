package cl.duoc.nwapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.nwapp.model.Datos
import cl.duoc.nwapp.repository.DatosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DatosViewModel(
    private val repository: DatosRepository,
) : ViewModel() {
    val nombre = MutableStateFlow("")
    val latitud = MutableStateFlow("")
    val longitud = MutableStateFlow("")

    // Use a private MutableStateFlow as a "backing property"
    private val _datos = MutableStateFlow<List<Datos>>(emptyList())
    // Expose an immutable StateFlow to the UI
    val datos = _datos.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            // Collect the Flow from the repository and update the StateFlow's value
            repository.getAll().collect { listaDeDatos ->
                _datos.value = listaDeDatos
            }
        }
    }

    fun agregarDatos(datos: Datos) {
        viewModelScope.launch {
            repository.insert(datos)
            // No need to call cargarDatos() anymore, the flow will update automatically
        }
    }

    fun actualizarDatos(datos: Datos) {
        viewModelScope.launch {
            repository.update(datos)
            // The flow will update automatically
        }
    }

    fun eliminarDatos(datos: Datos) {
        viewModelScope.launch {
            repository.delete(datos)
            // The flow will update automatically
        }
    }
}
