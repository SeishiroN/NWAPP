
package cl.duoc.nwapp.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DatosDao {
    @Query("SELECT * FROM datos ORDER BY id DESC")
    fun getAll(): Flow<List<Datos>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(datos: Datos)

    @Update
    suspend fun update(datos: Datos)

    @Delete
    suspend fun delete(datos: Datos)
}
