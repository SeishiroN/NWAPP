package cl.duoc.nwapp.model

import androidx.room.*

@Dao
interface FormularioModelDao {
    @Query("SELECT * FROM FormularioModel LIMIT 3")
    suspend fun getAll(): List<Cancion>

    @Insert
    suspend fun insert(cancion: Cancion)

    @Update
    suspend fun update(cancion: Cancion)

    @Delete
    suspend fun delete(cancion: Cancion)
}