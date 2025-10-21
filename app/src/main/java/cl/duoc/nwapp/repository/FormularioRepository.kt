// Archivo: repository/FormularioRepository.kt
package cl.duoc.nwapp.repository

import cl.duoc.nwapp.model.FormularioModel
import cl.duoc.nwapp.model.MensajesError

/**
 * El Repositorio del formulario actúa como una única fuente de verdad para los datos del formulario.
 * Su responsabilidad es manejar la lógica de negocio, como las reglas de validación,
 * y proporcionar los datos al ViewModel. En una app más compleja, aquí es donde se
 * interactuaría con bases de datos, APIs de red, etc.
 */
class  FormularioRepository {

    // Instancias privadas del modelo de datos y de los mensajes de error.
    // El repositorio "posee" estos datos.
    private var formulario = FormularioModel()
    private var errores = MensajesError()

    /** Retorna la instancia única del modelo del formulario. */
    fun getFormulario():  FormularioModel = formulario

    /** Retorna la instancia única de los mensajes de error. */
    fun getMensajesError():  MensajesError = errores

    /**
     * Regla de negocio para validar el nombre.
     * En este caso, la regla es que el nombre debe ser exactamente "ADMIN".
     * @return `true` si el nombre es válido, `false` en caso contrario.
     */
    fun validacionNombre(): Boolean {
        // La lógica podría ser más compleja, como verificar en una base de datos.
        return formulario.nombre == "ADMIN"
    }

    /**
     * Valida que el correo electrónico tenga un formato estándar.
     * Utiliza una expresión regular (Regex) para verificar la estructura del correo.
     * @return `true` si el correo tiene un formato válido, `false` en caso contrario.
     */
    fun validacionCorreo(): Boolean {
        // Regex para un formato de email simple: texto@texto.texto
        return formulario.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
    }

    /**
     * Valida que la edad sea un número entero y se encuentre en un rango específico.
     * @return `true` si la edad es un número entre 18 y 120 (inclusive), `false` en caso contrario.
     */
    fun validacionEdad(): Boolean {
        val edadInt = formulario.edad.toIntOrNull() // Intenta convertir el String a Int. Si falla, es null.
        // La validación falla si no es un número (null), o si está fuera del rango.
        return edadInt != null && edadInt >= 18 && edadInt <= 120
    }

    /**
     * Valida que los términos y condiciones hayan sido aceptados.
     * @return `true` si la propiedad `terminos` del modelo es `true`, `false` en caso contrario.
     */
    fun validacionTerminos(): Boolean {
        return formulario.terminos
    }
}