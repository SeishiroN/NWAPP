// Archivo: viewmodel/FormularioViewModel.kt
package cl.duoc.nwapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.duoc.nwapp.repository.FormularioRepository
import cl.duoc.nwapp.model.FormularioModel
import cl.duoc.nwapp.model.MensajesError

/**
 * ViewModel para la pantalla del formulario de creación de cuenta.
 *
 * En la arquitectura MVVM, el ViewModel actúa como un intermediario entre el Repositorio (lógica de datos)
 * y la UI (la vista). Su responsabilidad es obtener y preparar los datos para la UI,
 * y reaccionar a las interacciones del usuario (por ejemplo, validando un formulario).
 *
 * Al usar un ViewModel, los datos de la UI (como el estado del formulario) sobreviven a cambios de
 * configuración, como la rotación de la pantalla.
 */
class FormularioViewModel : ViewModel() {
    // Se crea una instancia del Repositorio para acceder a la lógica de negocio y validación de datos.
    private val repository = FormularioRepository()

    // 'formulario' contiene el estado actual de los campos del formulario (nombre, correo, etc.).
    // Se utiliza `mutableStateOf` de Jetpack Compose para que la UI se redibuje automáticamente
    // cada vez que el valor de 'formulario' cambia.
    var formulario: FormularioModel by mutableStateOf( repository.getFormulario() )

    // 'mensajesError' almacena los mensajes de error de validación para cada campo.
    // Al igual que 'formulario', es un estado mutable para que la UI reaccione a los cambios.
    var mensajesError: MensajesError by mutableStateOf( repository.getMensajesError() )

    /**
     * Verifica que todos los campos del formulario sean válidos ejecutando todas las
     * funciones de verificación individuales.
     * Retorna `true` solo si todas las validaciones son exitosas.
     */
    fun verificarFormulario(): Boolean {
        return verificarNombre() &&
                verificarCorreo() &&
                verificarEdad() &&
                verificarTerminos()
    }

    /**
     * Valida el campo 'nombre' a través del repositorio.
     * Si no es válido, asigna un mensaje de error y retorna `false`.
     * Si es válido, limpia el mensaje de error y retorna `true`.
     */
    fun verificarNombre(): Boolean {
        if (!repository.validacionNombre()) {
            mensajesError.nombre = "El nombre debe ser el de un usuario registrado"
            return false
        } else {
            mensajesError.nombre = ""
            return true
        }
    }

    /**
     * Valida el campo 'correo' a través del repositorio.
     * Actualiza el mensaje de error correspondiente y retorna el resultado de la validación.
     */
    fun verificarCorreo(): Boolean {
        if(!repository.validacionCorreo()) {
            mensajesError.correo = "El correo no es válido"
            return false
        } else {
            mensajesError.correo = ""
            return true
        }
    }

    /**
     * Valida el campo 'edad' a través del repositorio.
     * Actualiza el mensaje de error correspondiente y retorna el resultado de la validación.
     */
    fun verificarEdad(): Boolean {
        if(!repository.validacionEdad()) {
            mensajesError.edad = "La edad debe ser un número entre 18 y 120"
            return false
        } else {
            mensajesError.edad = ""
            return true
        }
    }

    /**
     * Valida si los términos y condiciones han sido aceptados.
     * Actualiza el mensaje de error correspondiente y retorna el resultado de la validación.
     */
    fun verificarTerminos(): Boolean {
        if(!repository.validacionTerminos()) {
            mensajesError.terminos = "Debes aceptar los términos"
            return false
        } else {
            mensajesError.terminos = ""
            return true
        }
    }
}
