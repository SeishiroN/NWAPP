
package cl.duoc.nwapp.model

import androidx.room.* // Importa todas las anotaciones de Room (Dao, Query, Insert, etc.).
import kotlinx.coroutines.flow.Flow // Importa Flow, un flujo de datos asíncrono de Kotlin Coroutines.

/**
 * DAO (Data Access Object) para la entidad `Datos`.
 * Esta interfaz es la única que tiene acceso directo a la tabla "datos" en la base de datos.
 * Room se encarga de generar la implementación de esta interfaz automáticamente.
 */
@Dao // La anotación @Dao le dice a Room que esta es una interfaz de acceso a datos.
interface DatosDao {

    /**
     * Obtiene todos los registros de la tabla `datos`, ordenados por ID de forma descendente.
     * @return Un `Flow<List<Datos>>`. Room se encarga de que este Flow emita una nueva lista
     * cada vez que los datos de la tabla cambien. La UI (a través del ViewModel) puede
     * "recolectar" este flujo para reaccionar a los cambios en tiempo real.
     */
    @Query("SELECT * FROM datos ORDER BY id DESC")
    fun getAll(): Flow<List<Datos>>

    /**
     * Inserta un nuevo registro en la tabla `datos`.
     * La palabra clave `suspend` indica que esta es una función de larga duración que debe
     * ser llamada desde una corrutina para no bloquear el hilo principal.
     * @param onConflict `OnConflictStrategy.REPLACE` significa que si intentas insertar un
     * dato con un `id` que ya existe, Room reemplazará el registro antiguo por el nuevo.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(datos: Datos)

    /**
     * Actualiza un registro existente.
     * `suspend` es necesario porque es una operación de disco.
     */
    @Update
    suspend fun update(datos: Datos)

    /**
     * Elimina un registro de la tabla.
     * `suspend` es necesario porque es una operación de disco.
     */
    @Delete
    suspend fun delete(datos: Datos)
}
