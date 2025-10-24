package cl.duoc.nwapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // Importa el ámbito de corrutinas atado al ciclo de vida del ViewModel.
import cl.duoc.nwapp.model.Datos
import cl.duoc.nwapp.repository.DatosRepository
import kotlinx.coroutines.flow.MutableStateFlow // Un Flow especial que permite emitir y recolectar valores de estado.
import kotlinx.coroutines.flow.asStateFlow // Convierte un MutableStateFlow en un StateFlow de solo lectura para la UI.
import kotlinx.coroutines.launch // Lanza una nueva corrutina sin bloquear el hilo actual.

/**
 * ViewModel para gestionar la lógica de la pantalla de datos (ubicaciones).
 * Sigue el patrón MVVM: la UI observa los datos del ViewModel y le notifica eventos.
 * @param repository El repositorio es la única fuente de datos para el ViewModel.
 */
class DatosViewModel(
    private val repository: DatosRepository,
) : ViewModel() {

    // --- ESTADOS PARA LOS CAMPOS DEL FORMULARIO ---
    val nombre = MutableStateFlow("")
    val latitud = MutableStateFlow("")
    val longitud = MutableStateFlow("")

    // --- ESTADOS PARA LOS ERRORES DE VALIDACIÓN ---
    val nombreError = MutableStateFlow("")
    val latitudError = MutableStateFlow("")
    val longitudError = MutableStateFlow("")

    // --- ESTADO PARA LA LISTA DE DATOS ---
    private val _datos = MutableStateFlow<List<Datos>>(emptyList())
    val datos = _datos.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            repository.getAll().collect { listaDeDatos ->
                _datos.value = listaDeDatos
            }
        }
    }

    fun agregarDatos(datos: Datos) {
        viewModelScope.launch {
            repository.insert(datos)
        }
    }

    fun eliminarDatos(datos: Datos) {
        viewModelScope.launch {
            repository.delete(datos)
        }
    }

    // --- FUNCIONES DE VALIDACIÓN INDIVIDUAL ---

    fun validarNombre(): Boolean {
        return if (nombre.value.isBlank()) {
            nombreError.value = "El nombre es obligatorio"
            false
        } else {
            nombreError.value = ""
            true
        }
    }

    fun validarLatitud(): Boolean {
        return if (latitud.value.isBlank()) {
            latitudError.value = "La latitud es obligatoria"
            false
        } else if (latitud.value.toDoubleOrNull() == null) {
            latitudError.value = "Debe ser un número válido (ej: -33.5)"
            false
        } else {
            latitudError.value = ""
            true
        }
    }

    fun validarLongitud(): Boolean {
        return if (longitud.value.isBlank()) {
            longitudError.value = "La longitud es obligatoria"
            false
        } else if (longitud.value.toDoubleOrNull() == null) {
            longitudError.value = "Debe ser un número válido (ej: -70.6)"
            false
        } else {
            longitudError.value = ""
            true
        }
    }

    /**
     * Valida todos los campos del formulario a la vez, útil para el botón de submit.
     * @return `true` si todos los campos son válidos, `false` en caso contrario.
     */
    fun validarCampos(): Boolean {
        // Llama a las validaciones individuales para asegurarse de que todos los errores se muestren.
        val nombreValido = validarNombre()
        val latitudValida = validarLatitud()
        val longitudValida = validarLongitud()
        return nombreValido && latitudValida && longitudValida
    }
}
