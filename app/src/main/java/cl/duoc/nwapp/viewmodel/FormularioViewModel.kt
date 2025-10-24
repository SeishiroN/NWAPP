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
 * En la arquitectura MVVM, el ViewModel actúa como un intermediario entre la capa de datos (Repositorio)
 * y la capa de UI (la Vista). Su responsabilidad es:
 * 1. Mantener el estado de la UI (los datos que se muestran en pantalla).
 * 2. Exponer ese estado a la UI para que lo observe.
 * 3. Contener la lógica de negocio y reaccionar a los eventos del usuario (clics, texto ingresado, etc.).
 *
 * Al usar un ViewModel, el estado (como los datos del formulario) sobrevive a cambios de configuración,
 * por ejemplo, si el usuario rota la pantalla, la información no se pierde.
 */
class FormularioViewModel : ViewModel() {
    // Se crea una instancia del Repositorio. En este caso, el repositorio contiene la lógica de validación.
    private val repository = FormularioRepository()

    // --- ESTADO DEL FORMULARIO ---

    // `formulario` contiene el estado actual de todos los campos (nombre, correo, etc.).
    // Se inicializa con los valores por defecto que provee el repositorio.
    // `by mutableStateOf` es un delegado de propiedad de Compose. Esto significa que:
    //  - `formulario` es un estado observable.
    //  - Cada vez que una propiedad de `formulario` cambia (ej: `formulario.nombre = "nuevo"`),
    //    Compose lo detecta y recompone automáticamente las partes de la UI que dependen de él.
    var formulario: FormularioModel by mutableStateOf(repository.getFormulario())

    // `mensajesError` almacena los textos de error para cada campo.
    // También es un estado observable para que la UI pueda mostrar u ocultar los errores en tiempo real.
    var mensajesError: MensajesError by mutableStateOf(repository.getMensajesError())

    /**
     * Función maestra de validación. Comprueba todos los campos del formulario a la vez.
     * La UI usa esta función para, por ejemplo, habilitar o deshabilitar el botón de "Ingresar".
     * @return `true` solo si TODAS las funciones de verificación individuales devuelven `true`.
     */
    fun verificarFormulario(): Boolean {
        return verificarNombre() &&
            verificarCorreo() &&
            verificarEdad() &&
            verificarTerminos()
    }

    /**
     * Valida el campo 'nombre' llamando a la lógica del repositorio.
     * Si la validación falla, actualiza el estado `mensajesError.nombre` con un texto.
     * Si la validación es exitosa, limpia el mensaje de error.
     * @return `true` si el nombre es válido, `false` en caso contrario.
     */
    fun verificarNombre(): Boolean {
        // La lógica real (ej: `formulario.nombre == "admin"`) está en el repositorio.
        return if (!repository.validacionNombre()) {
            mensajesError.nombre = "El nombre debe ser el de un usuario registrado"
            false
        } else {
            mensajesError.nombre = ""
            true
        }
    }

    /**
     * Valida el campo 'correo'.
     */
    fun verificarCorreo(): Boolean {
        return if (!repository.validacionCorreo()) {
            mensajesError.correo = "El correo no es válido"
            false
        } else {
            mensajesError.correo = ""
            true
        }
    }

    /**
     * Valida el campo 'edad'.
     */
    fun verificarEdad(): Boolean {
        return if (!repository.validacionEdad()) {
            mensajesError.edad = "La edad debe ser un número entre 18 y 120"
            false
        } else {
            mensajesError.edad = ""
            true
        }
    }

    /**
     * Valida si la casilla de términos y condiciones fue marcada.
     */
    fun verificarTerminos(): Boolean {
        return if (!repository.validacionTerminos()) {
            mensajesError.terminos = "Debes aceptar los términos"
            false
        } else {
            mensajesError.terminos = ""
            true
        }
    }
}
