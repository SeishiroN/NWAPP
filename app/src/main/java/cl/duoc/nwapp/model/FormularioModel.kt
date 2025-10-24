// Archivo: model/FormularioModel.kt
package cl.duoc.nwapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * `FormularioModel` es una clase de estado que representa los datos del formulario de creación de cuenta.
 *
 * A diferencia de `Datos`, esta no es una `data class` de Room y no se guarda en la base de datos.
 * Es una clase mutable diseñada para mantener el estado de la UI mientras el usuario interactúa con ella.
 *
 * Cada propiedad se define con `var` y se delega a `mutableStateOf`. Esto es crucial para que Compose
 * pueda observar los cambios. Cuando una propiedad cambia (ej: `nombre = "nuevo valor"`), Compose
 * detecta este cambio y automáticamente "recompone" (vuelve a dibujar) las partes de la UI
 * que dependen de esa propiedad, como los `OutlinedTextField`.
 */
class FormularioModel {
    var nombre: String by mutableStateOf("")
    var correo: String by mutableStateOf("")
    var edad: String by mutableStateOf("")
    var terminos: Boolean by mutableStateOf(false)
}
