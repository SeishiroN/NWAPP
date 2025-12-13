package cl.duoc.nwapp.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * PRUEBA UNITARIA MÁS SENCILLA: Modelo de Datos
 *
 * Esta prueba verifica que el objeto Datos funcione correctamente.
 * Es la más fácil de entender porque solo crea objetos y verifica sus valores.
 */
class DatosTest {
    @Test
    fun `cuando se crea un Datos con valores, debe contener esos valores`() {
        // Arrange (Preparar) - Creamos los datos de prueba
        val nombre = "Parque Arauco"
        val latitud = "-33.4569"
        val longitud = "-70.6483"

        // Act (Actuar) - Creamos el objeto
        val datos =
            Datos(
                nombre = nombre,
                latitud = latitud,
                longitud = longitud,
            )

        // Assert (Afirmar) - Verificamos que los valores sean correctos
        assertThat(datos.nombre).isEqualTo("Parque Arauco")
        assertThat(datos.latitud).isEqualTo("-33.4569")
        assertThat(datos.longitud).isEqualTo("-70.6483")
    }

    @Test
    fun `cuando se crea un Datos sin ID, debe tener ID por defecto 0`() {
        // Act
        val datos =
            Datos(
                nombre = "Test",
                latitud = "0.0",
                longitud = "0.0",
            )

        // Assert
        assertThat(datos.id).isEqualTo(0)
    }

    @Test
    fun `cuando se crea un Datos con ID, debe mantener ese ID`() {
        // Act
        val datos =
            Datos(
                id = 5,
                nombre = "Test",
                latitud = "0.0",
                longitud = "0.0",
            )

        // Assert
        assertThat(datos.id).isEqualTo(5)
    }

    @Test
    fun `dos objetos Datos con los mismos valores deben ser iguales`() {
        // Arrange
        val datos1 = Datos(id = 1, nombre = "Plaza", latitud = "10.0", longitud = "20.0")
        val datos2 = Datos(id = 1, nombre = "Plaza", latitud = "10.0", longitud = "20.0")

        // Assert - data class genera equals() automáticamente
        assertThat(datos1).isEqualTo(datos2)
    }

    @Test
    fun `dos objetos Datos con diferentes valores no deben ser iguales`() {
        // Arrange
        val datos1 = Datos(id = 1, nombre = "Plaza", latitud = "10.0", longitud = "20.0")
        val datos2 = Datos(id = 2, nombre = "Parque", latitud = "30.0", longitud = "40.0")

        // Assert
        assertThat(datos1).isNotEqualTo(datos2)
    }
}
