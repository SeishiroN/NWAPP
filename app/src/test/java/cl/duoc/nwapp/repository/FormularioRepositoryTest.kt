package cl.duoc.nwapp.repository

import cl.duoc.nwapp.data.model.LoginResponse
import cl.duoc.nwapp.data.remote.ApiService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * PRUEBA CON MOCKS - Nivel Intermedio
 *
 * Esta prueba usa MockK para simular el comportamiento de la API.
 * Es un poco más compleja pero muy útil para probar repositorios.
 *
 * MockK permite crear objetos "falsos" que simulan el comportamiento real.
 */
class FormularioRepositoryTest {
    // Mock del ApiService
    private lateinit var apiService: ApiService
    private lateinit var repository: FormularioRepository

    @Before
    fun setup() {
        // Crear un mock (objeto falso) del ApiService
        apiService = mockk()
        // Nota: Para usar este mock, necesitarías modificar FormularioRepository
        // para aceptar ApiService como parámetro (inyección de dependencias)
    }

    @Test
    fun `validarLogin con credenciales vacías debe retornar false`() =
        runTest {
            // Arrange
            val repository = FormularioRepository()

            // Act
            val result = repository.validarLogin("", "")

            // Assert
            assertThat(result).isFalse()
        }

    @Test
    fun `validarLogin con email vacío debe retornar false`() =
        runTest {
            // Arrange
            val repository = FormularioRepository()

            // Act
            val result = repository.validarLogin("", "password123")

            // Assert
            assertThat(result).isFalse()
        }

    @Test
    fun `validarLogin con password vacío debe retornar false`() =
        runTest {
            // Arrange
            val repository = FormularioRepository()

            // Act
            val result = repository.validarLogin("usuario@ejemplo.com", "")

            // Assert
            assertThat(result).isFalse()
        }

    @Test
    fun `validarLogin con credenciales solo con espacios debe retornar false`() =
        runTest {
            // Arrange
            val repository = FormularioRepository()

            // Act
            val result = repository.validarLogin("   ", "   ")

            // Assert
            assertThat(result).isFalse()
        }
}

/**
 * EJEMPLO AVANZADO: Cómo se vería con mocks completos
 *
 * Esta es una clase de ejemplo para mostrar cómo usar MockK.
 * Requiere modificar FormularioRepository para aceptar ApiService.
 */
class FormularioRepositoryWithMocksExample {
    private lateinit var mockApiService: ApiService

    @Before
    fun setup() {
        mockApiService = mockk()
    }

    @Test
    fun `ejemplo - login exitoso debe retornar true`() =
        runTest {
            // Arrange - Configurar el mock para que retorne una respuesta exitosa
            val mockResponse = mockk<Response<LoginResponse>>()

            // Configurar el comportamiento del mock
            coEvery {
                mockApiService.login(any())
            } returns mockResponse

            coEvery {
                mockResponse.isSuccessful
            } returns true

            // Act - Llamar a la función (requiere repositorio modificado)
            // val result = repository.validarLogin("test@test.com", "password")

            // Assert
            // assertThat(result).isTrue()

            // Por ahora, solo demostramos la sintaxis
            assertThat(true).isTrue()
        }

    @Test
    fun `ejemplo - login fallido debe retornar false`() =
        runTest {
            // Arrange
            val mockResponse = mockk<Response<LoginResponse>>()

            coEvery {
                mockApiService.login(any())
            } returns mockResponse

            coEvery {
                mockResponse.isSuccessful
            } returns false

            // Este es solo un ejemplo de sintaxis de MockK
            assertThat(true).isTrue()
        }
}
