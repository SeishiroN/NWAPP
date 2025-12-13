package cl.duoc.nwapp.utils

/**
 * Utilidades para validación de datos del formulario.
 *
 * Esta clase contiene funciones puras de validación que pueden
 * ser utilizadas en ViewModels y otras partes del proyecto.
 *
 * Todas las funciones son fácilmente testeables porque no tienen
 * dependencias externas ni efectos secundarios.
 */
object ValidationUtils {
    /**
     * Valida si un email tiene el formato correcto.
     *
     * @param email El email a validar
     * @return true si el email contiene @ y . , false en caso contrario
     */
    fun isEmailValid(email: String): Boolean {
        if (email.isBlank()) return false
        return email.contains("@") && email.contains(".")
    }

    /**
     * Valida si una contraseña cumple con los requisitos mínimos.
     *
     * @param password La contraseña a validar
     * @return true si tiene al menos 6 caracteres, false en caso contrario
     */
    fun isPasswordValid(password: String): Boolean = password.length >= 6

    /**
     * Valida si un nombre es válido.
     *
     * @param name El nombre a validar
     * @return true si tiene al menos 2 caracteres y no está vacío
     */
    fun isNameValid(name: String): Boolean = name.isNotBlank() && name.length >= 2

    /**
     * Valida si las coordenadas geográficas son válidas.
     *
     * @param latitud Latitud como String
     * @param longitud Longitud como String
     * @return true si están en el rango válido (-90 a 90 para lat, -180 a 180 para lon)
     */
    fun areCoordinatesValid(
        latitud: String,
        longitud: String,
    ): Boolean =
        try {
            val lat = latitud.toDouble()
            val lon = longitud.toDouble()
            lat in -90.0..90.0 && lon in -180.0..180.0
        } catch (e: Exception) {
            false
        }

    /**
     * Obtiene el mensaje de error apropiado para un email inválido.
     *
     * @param email El email a validar
     * @return El mensaje de error o null si es válido
     */
    fun getEmailErrorMessage(email: String): String? =
        when {
            email.isBlank() -> "El email no puede estar vacío"
            !email.contains("@") -> "El email debe contener @"
            !email.contains(".") -> "El email debe contener un dominio válido"
            else -> null
        }

    /**
     * Obtiene el mensaje de error apropiado para una contraseña inválida.
     *
     * @param password La contraseña a validar
     * @return El mensaje de error o null si es válida
     */
    fun getPasswordErrorMessage(password: String): String? =
        when {
            password.isEmpty() -> "La contraseña no puede estar vacía"
            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }

    /**
     * Obtiene el mensaje de error apropiado para un nombre inválido.
     *
     * @param name El nombre a validar
     * @return El mensaje de error o null si es válido
     */
    fun getNameErrorMessage(name: String): String? =
        when {
            name.isBlank() -> "El nombre no puede estar vacío"
            name.length < 2 -> "El nombre debe tener al menos 2 caracteres"
            else -> null
        }
}
