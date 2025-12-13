package cl.duoc.nwapp.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * PRUEBA UNITARIA MUY SENCILLA: Validaciones
 *
 * Esta clase prueba las funciones de validación de ValidationUtils.
 * Las pruebas son muy fáciles de entender: entrada -> salida esperada.
 *
 * NOTA: ValidationUtils está definido en app/src/main/java/cl/duoc/nwapp/utils/ValidationUtils.kt
 */
class ValidationUtilsTest {
    // ========== Pruebas de Email ==========

    @Test
    fun `email válido debe retornar true`() {
        val result = ValidationUtils.isEmailValid("usuario@ejemplo.com")
        assertThat(result).isTrue()
    }

    @Test
    fun `email sin arroba debe retornar false`() {
        val result = ValidationUtils.isEmailValid("usuarioejemplo.com")
        assertThat(result).isFalse()
    }

    @Test
    fun `email sin punto debe retornar false`() {
        val result = ValidationUtils.isEmailValid("usuario@ejemplocom")
        assertThat(result).isFalse()
    }

    @Test
    fun `email vacío debe retornar false`() {
        val result = ValidationUtils.isEmailValid("")
        assertThat(result).isFalse()
    }

    @Test
    fun `email con espacios en blanco debe retornar false`() {
        val result = ValidationUtils.isEmailValid("   ")
        assertThat(result).isFalse()
    }

    // ========== Pruebas de Password ==========

    @Test
    fun `password con 6 caracteres debe ser válido`() {
        val result = ValidationUtils.isPasswordValid("abc123")
        assertThat(result).isTrue()
    }

    @Test
    fun `password con más de 6 caracteres debe ser válido`() {
        val result = ValidationUtils.isPasswordValid("contraseñaSegura123")
        assertThat(result).isTrue()
    }

    @Test
    fun `password con menos de 6 caracteres debe ser inválido`() {
        val result = ValidationUtils.isPasswordValid("12345")
        assertThat(result).isFalse()
    }

    @Test
    fun `password vacío debe ser inválido`() {
        val result = ValidationUtils.isPasswordValid("")
        assertThat(result).isFalse()
    }

    // ========== Pruebas de Nombre ==========

    @Test
    fun `nombre válido debe retornar true`() {
        val result = ValidationUtils.isNameValid("Juan Pérez")
        assertThat(result).isTrue()
    }

    @Test
    fun `nombre con 2 caracteres debe ser válido`() {
        val result = ValidationUtils.isNameValid("AB")
        assertThat(result).isTrue()
    }

    @Test
    fun `nombre con 1 caracter debe ser inválido`() {
        val result = ValidationUtils.isNameValid("A")
        assertThat(result).isFalse()
    }

    @Test
    fun `nombre vacío debe ser inválido`() {
        val result = ValidationUtils.isNameValid("")
        assertThat(result).isFalse()
    }

    @Test
    fun `nombre con solo espacios debe ser inválido`() {
        val result = ValidationUtils.isNameValid("   ")
        assertThat(result).isFalse()
    }

    // ========== Pruebas de Coordenadas ==========

    @Test
    fun `coordenadas válidas de Santiago deben retornar true`() {
        val result = ValidationUtils.areCoordinatesValid("-33.4569", "-70.6483")
        assertThat(result).isTrue()
    }

    @Test
    fun `coordenadas en el rango válido deben retornar true`() {
        val result = ValidationUtils.areCoordinatesValid("45.5", "120.3")
        assertThat(result).isTrue()
    }

    @Test
    fun `latitud mayor a 90 debe retornar false`() {
        val result = ValidationUtils.areCoordinatesValid("91.0", "50.0")
        assertThat(result).isFalse()
    }

    @Test
    fun `latitud menor a -90 debe retornar false`() {
        val result = ValidationUtils.areCoordinatesValid("-91.0", "50.0")
        assertThat(result).isFalse()
    }

    @Test
    fun `longitud mayor a 180 debe retornar false`() {
        val result = ValidationUtils.areCoordinatesValid("45.0", "181.0")
        assertThat(result).isFalse()
    }

    @Test
    fun `coordenadas con texto inválido deben retornar false`() {
        val result = ValidationUtils.areCoordinatesValid("abc", "def")
        assertThat(result).isFalse()
    }

    @Test
    fun `coordenadas vacías deben retornar false`() {
        val result = ValidationUtils.areCoordinatesValid("", "")
        assertThat(result).isFalse()
    }
}
