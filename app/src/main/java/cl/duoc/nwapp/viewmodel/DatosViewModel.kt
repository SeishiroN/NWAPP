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
    // `MutableStateFlow` se usa para mantener el estado actual de cada campo de texto.
    // La UI observará estos Flows y se actualizará automáticamente cuando cambien.
    val nombre = MutableStateFlow("")
    val latitud = MutableStateFlow("")
    val longitud = MutableStateFlow("")

    // --- ESTADOS PARA LOS ERRORES DE VALIDACIÓN ---
    // Cada campo tiene un Flow asociado para su mensaje de error.
    // Si el string está vacío, no hay error. Si tiene texto, la UI lo mostrará.
    val nombreError = MutableStateFlow("")
    val latitudError = MutableStateFlow("")
    val longitudError = MutableStateFlow("")

    // --- ESTADO PARA LA LISTA DE DATOS ---
    // `_datos` es un StateFlow mutable y privado. Solo el ViewModel puede cambiar su valor.
    private val _datos = MutableStateFlow<List<Datos>>(emptyList())
    // `datos` es un StateFlow público y de solo lectura. La UI lo observa para mostrar la lista,
    // pero no puede modificarla directamente. Esto garantiza un flujo de datos unidireccional.
    val datos = _datos.asStateFlow()

    // El bloque `init` se ejecuta una sola vez cuando el ViewModel es creado.
    init {
        cargarDatos() // Llama a la función para cargar los datos desde la BD al iniciar.
    }

    // Carga los datos desde el repositorio.
    private fun cargarDatos() {
        // `viewModelScope.launch` inicia una corrutina que se cancelará automáticamente
        // si el ViewModel es destruido (ej: el usuario cierra la pantalla).
        viewModelScope.launch {
            // `repository.getAll()` devuelve un Flow. `collect` se suscribe a él y recibe
            // una nueva lista cada vez que los datos en la BD cambian.
            repository.getAll().collect { listaDeDatos ->
                _datos.value = listaDeDatos // Actualiza el estado con la nueva lista.
            }
        }
    }

    // Función pública para que la UI pueda solicitar agregar datos.
    fun agregarDatos(datos: Datos) {
        viewModelScope.launch {
            repository.insert(datos) // Llama a la función `suspend` del repositorio.
        }
    }

    // Función pública para que la UI pueda solicitar eliminar datos.
    fun eliminarDatos(datos: Datos) {
        viewModelScope.launch {
            repository.delete(datos) // Llama a la función `suspend` del repositorio.
        }
    }

    /**
     * Valida los campos del formulario y actualiza los estados de error.
     * @return `true` si todos los campos son válidos, `false` en caso contrario.
     */
    fun validarCampos(): Boolean {
        // Primero, reseteamos los errores para una nueva validación.
        nombreError.value = ""
        latitudError.value = ""
        longitudError.value = ""

        var esValido = true

        if (nombre.value.isBlank()) {
            nombreError.value = "El nombre es obligatorio" // Actualiza el estado de error.
            esValido = false
        }

        if (latitud.value.isBlank()) {
            latitudError.value = "La latitud es obligatoria"
            esValido = false
        } else if (latitud.value.toDoubleOrNull() == null) { // Comprueba si el String se puede convertir a Double.
            latitudError.value = "Debe ser un número válido (ej: -33.5)"
            esValido = false
        }

        if (longitud.value.isBlank()) {
            longitudError.value = "La longitud es obligatoria"
            esValido = false
        } else if (longitud.value.toDoubleOrNull() == null) {
            longitudError.value = "Debe ser un número válido (ej: -70.6)"
            esValido = false
        }

        return esValido
    }
}
