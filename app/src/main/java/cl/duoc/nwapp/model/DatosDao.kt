package cl.duoc.nwapp.model

import androidx.room.*

@Dao
interface DatosDao {
    @Query("SELECT * FROM datos LIMIT 3")
    suspend fun getAll(): List<Datos>

    @Insert
    suspend fun insert(datos: Datos)

    @Update
    suspend fun update(datos: Datos)

    @Delete
    suspend fun delete(datos: Datos)
}