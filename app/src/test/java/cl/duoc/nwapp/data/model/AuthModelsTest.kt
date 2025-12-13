package cl.duoc.nwapp.data.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * PRUEBA UNITARIA SENCILLA: Modelos de Request/Response
 *
 * Verifica que los modelos para la API funcionen correctamente.
 * Útil para asegurar que los datos se envían y reciben correctamente.
 */
class AuthModelsTest {
    @Test
    fun `LoginRequest debe crear objeto con email y password correctos`() {
        // Arrange
        val email = "usuario@ejemplo.com"
        val password = "contraseña123"

        // Act
        val request = LoginRequest(email = email, password = password)

        // Assert
        assertThat(request.email).isEqualTo("usuario@ejemplo.com")
        assertThat(request.password).isEqualTo("contraseña123")
    }

    @Test
    fun `SignupRequest debe crear objeto con name, email y password correctos`() {
        // Arrange
        val name = "Juan Pérez"
        val email = "juan@ejemplo.com"
        val password = "miPassword"

        // Act
        val request = SignupRequest(name = name, email = email, password = password)

        // Assert
        assertThat(request.name).isEqualTo("Juan Pérez")
        assertThat(request.email).isEqualTo("juan@ejemplo.com")
        assertThat(request.password).isEqualTo("miPassword")
    }

    @Test
    fun `dos LoginRequest con los mismos valores deben ser iguales`() {
        // Arrange
        val request1 = LoginRequest("test@test.com", "pass123")
        val request2 = LoginRequest("test@test.com", "pass123")

        // Assert
        assertThat(request1).isEqualTo(request2)
    }

    @Test
    fun `LoginRequest con email vacío debe permitirse crear`() {
        // Act
        val request = LoginRequest(email = "", password = "pass")

        // Assert - El modelo permite crearse, la validación es en otra capa
        assertThat(request.email).isEmpty()
    }

    @Test
    fun `SignupRequest con campos vacíos debe permitirse crear`() {
        // Act
        val request = SignupRequest(name = "", email = "", password = "")

        // Assert - El modelo permite crearse, la validación es en otra capa
        assertThat(request.name).isEmpty()
        assertThat(request.email).isEmpty()
        assertThat(request.password).isEmpty()
    }
}
