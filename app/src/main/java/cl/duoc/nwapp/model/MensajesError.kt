// Archivo: model/MensajesError.kt
package cl.duoc.nwapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * `MensajesError` es una clase de estado, similar a `FormularioModel`.
 * Su única responsabilidad es mantener el estado de los mensajes de error para cada campo
 * del formulario de creación de cuenta.
 *
 * Al igual que en `FormularioModel`, cada propiedad es un estado observable (`mutableStateOf`).
 * Esto permite que el `FormularioViewModel` actualice un mensaje de error (ej: `mensajesError.nombre = "Error"`)
 * y que la UI, que está observando este estado, se recomponga automáticamente para mostrar el texto
 * de error en rojo debajo del campo correspondiente.
 *
 * Si una propiedad está vacía (`""`), significa que no hay error para ese campo.
 */
class MensajesError {
    var nombre: String by mutableStateOf("")
    var correo: String by mutableStateOf("")
    var edad: String by mutableStateOf("")
    var terminos: String by mutableStateOf("")
}
