// Archivo: repository/DatosRepository.kt
package cl.duoc.nwapp.repository

import cl.duoc.nwapp.model.Datos
import cl.duoc.nwapp.model.DatosDao
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio para la entidad `Datos`.
 *
 * El patrón de Repositorio es una práctica recomendada en la arquitectura de Android.
 * Su función es actuar como una capa de abstracción entre la fuente de datos (en este caso, el DAO de Room)
 * y la capa de lógica de negocio (el ViewModel).
 *
 * Beneficios:
 * 1.  **Centraliza el acceso a datos**: El ViewModel no sabe (ni le importa) si los datos vienen de una
 *     base de datos local, una API de red o un archivo. Solo le pide los datos al Repositorio.
 * 2.  **Facilita las pruebas**: Se puede crear un "falso" repositorio para las pruebas unitarias del
 *     ViewModel, permitiendo simular diferentes escenarios sin necesidad de una base de datos real.
 * 3.  **Flexibilidad**: Si en el futuro decides añadir una fuente de datos en la nube, solo tendrías
 *     que modificar el Repositorio para que sincronice los datos, sin tocar el ViewModel.
 */
class DatosRepository(
    // El repositorio recibe el DAO como una dependencia (inyección de dependencias).
    // Esto lo hace más modular y fácil de probar.
    private val datosDao: DatosDao
) {

    /**
     * Expone el `Flow` de datos directamente desde el DAO.
     * El ViewModel se suscribirá a este Flow para recibir actualizaciones en tiempo real.
     */
    fun getAll(): Flow<List<Datos>> = datosDao.getAll()

    /**
     * Delega la operación de inserción al método correspondiente del DAO.
     * La función está marcada como `suspend` porque la operación de base de datos debe
     * ejecutarse en una corrutina para no bloquear el hilo principal.
     */
    suspend fun insert(datos: Datos) {
        datosDao.insert(datos)
    }

    /**
     * Delega la operación de eliminación al DAO.
     */
    suspend fun delete(datos: Datos) {
        datosDao.delete(datos)
    }
}
