
package cl.duoc.nwapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.duoc.nwapp.model.UbicacionGuardada
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) para la tabla 'ubicaciones'.
 * Aquí se definen todas las interacciones con la base de datos.
 */
@Dao
interface UbicacionDao {

    /**
     * Inserta una nueva ubicación en la tabla.
     * `OnConflictStrategy.IGNORE` indica que si intentamos insertar una ubicación
     * que ya existe (según su clave primaria), simplemente se ignora la operación.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(ubicacion: UbicacionGuardada)

    /**
     * Obtiene todas las ubicaciones de la tabla, ordenadas por ID de forma descendente (las más nuevas primero).
     * La función devuelve un `Flow<List<UbicacionGuardada>>`. Esto es muy potente:
     * Room se encargará de que este flujo emita automáticamente la nueva lista completa
     * cada vez que haya un cambio en la tabla (una inserción, borrado, etc.).
     * La UI puede simplemente "observar" este flujo y se actualizará sola.
     */
    @Query("SELECT * FROM ubicaciones ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<UbicacionGuardada>>
    
    /**
     * Borra todas las ubicaciones de la tabla.
     * Esta es una función útil para, por ejemplo, un botón de "Limpiar Historial".
     */
    @Query("DELETE FROM ubicaciones")
    suspend fun borrarTodo()
}
