// Archivo: repository/FormularioRepository.kt
package cl.duoc.nwapp.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cl.duoc.nwapp.model.FormularioModel
import cl.duoc.nwapp.model.MensajesError

/**
 * Repositorio para la lógica de validación del formulario de creación de cuenta.
 *
 * A diferencia de `DatosRepository`, este repositorio no se conecta a una fuente de datos externa
 * como una base de datos. Su principal responsabilidad es encapsular las reglas de negocio
 * y la lógica de validación, manteniendo al `FormularioViewModel` limpio y enfocado en
 * gestionar el estado de la UI.
 */
class FormularioRepository {
    // Se mantiene una instancia privada del modelo del formulario. Esta instancia
    // contiene los valores actuales que el usuario ha ingresado.
    private var formulario: FormularioModel by mutableStateOf(FormularioModel())

    // Se mantiene una instancia privada del modelo de mensajes de error.
    private var mensajesError: MensajesError by mutableStateOf(MensajesError())

    /**
     * Devuelve la instancia actual del estado del formulario.
     * El ViewModel usa esta función para obtener el objeto de estado que observará.
     */
    fun getFormulario(): FormularioModel {
        return formulario
    }

    /**
     * Devuelve la instancia actual de los mensajes de error.
     */
    fun getMensajesError(): MensajesError {
        return mensajesError
    }

    // --- FUNCIONES DE VALIDACIÓN ---
    // Cada función implementa una regla de negocio específica.

    /**
     * Valida que el nombre de usuario sea exactamente "admin".
     * @return `true` si el nombre es "admin", `false` en caso contrario.
     */
    fun validacionNombre(): Boolean {
        return formulario.nombre == "admin"
    }

    /**
     * Valida que el correo electrónico contenga un "@".
     * Esta es una validación simple; en una app real se usaría una expresión regular más robusta.
     * @return `true` si el correo contiene "@", `false` en caso contrario.
     */
    fun validacionCorreo(): Boolean {
        return formulario.correo.contains("@")
    }

    /**
     * Valida que la edad sea un número entre 18 y 120.
     * `toIntOrNull()` convierte el String a un entero de forma segura. Si el texto no es un número,
     * devuelve `null`, y el operador `?: 0` lo convierte a 0.
     * @return `true` si la edad está en el rango válido, `false` en caso contrario.
     */
    fun validacionEdad(): Boolean {
        val edadInt = formulario.edad.toIntOrNull() ?: 0
        return edadInt in 18..120
    }

    /**
     * Valida que la casilla de términos y condiciones haya sido marcada.
     * @return `true` si `terminos` es `true`, `false` en caso contrario.
     */
    fun validacionTerminos(): Boolean {
        return formulario.terminos
    }
}
