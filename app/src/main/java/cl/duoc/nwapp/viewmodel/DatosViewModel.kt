package cl.duoc.nwapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.nwapp.model.Datos
import cl.duoc.nwapp.repository.DatosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DatosViewModel(
    private val repository: DatosRepository,
) : ViewModel() {
    val nombre = MutableStateFlow("")
    val latitud = MutableStateFlow("")
    val longitud = MutableStateFlow("")

    val datos = MutableStateFlow<List<Datos>>(emptyList())
    // ...

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            datos.value = repository.getAll()
        }
    }

    fun agregarDatos(datos: Datos) {
        viewModelScope.launch {
            repository.insert(datos)
            cargarDatos()
        }
    }

    fun actualizarDatos(datos: Datos) {
        viewModelScope.launch {
            repository.update(datos)
            cargarDatos()
        }
    }

    fun eliminarDatos(datos: Datos) {
        viewModelScope.launch {
            repository.delete(datos)
            cargarDatos()
        }
    }
}
