// Archivo: model/FormularioModel.kt
package cl.duoc.nwapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Clase que representa el modelo de datos para el formulario de registro.
 * Contiene todos los campos que el usuario debe rellenar.
 */
class FormularioModel {
    // Cada propiedad de la clase es un estado mutable de Compose (`mutableStateOf`).
    // Esto significa que cualquier cambio en estas propiedades notificará a la UI
    // para que se redibuje y muestre el valor actualizado. El `by` es un delegado
    // de propiedad que simplifica el acceso, permitiendo usar `nombre` en lugar de `nombre.value`.

    /** El nombre del usuario. */
    var nombre by mutableStateOf("")

    /** El correo electrónico del usuario. */
    var correo by mutableStateOf("")

    /** La edad del usuario. */
    var edad by mutableStateOf("")

    /** `true` si el usuario ha aceptado los términos y condiciones. */
    var terminos by mutableStateOf(false)
}