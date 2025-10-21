// Archivo: model/MensajesError.kt
package cl.duoc.nwapp.model

/**
 * Clase que representa el modelo de datos para los mensajes de error del formulario.
 * Cada propiedad corresponde a un campo del formulario y almacenará el texto del error
 * de validación si es que existe. Esta clase ayuda a mantener el estado de los errores
 * de forma organizada.
 */
class MensajesError {
    /** Mensaje de error para el campo 'nombre'. Se muestra si la validación del nombre falla. */
    var nombre: String = ""

    /** Mensaje de error para el campo 'correo'. Se muestra si el formato del correo no es válido. */
    var correo: String = ""

    /** Mensaje de error para el campo 'edad'. Se muestra si la edad no está en el rango permitido. */
    var edad: String = ""

    /** Mensaje de error para el checkbox de 'términos'. Se muestra si los términos no son aceptados. */
    var terminos: String = ""
}
