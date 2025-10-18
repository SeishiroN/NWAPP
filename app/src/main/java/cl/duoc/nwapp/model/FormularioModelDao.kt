package cl.duoc.nwapp.model

import androidx.room.*

@Dao
interface FormularioModelDao {
    @Query("SELECT * FROM  personas LIMIT 3")
    suspend fun getAll(): List< FormularioModel>

    @Insert
    suspend fun insert(formulariomodel:  FormularioModel)

    @Update
    suspend fun update(formulariomodel:  FormularioModel)

    @Delete
    suspend fun delete(formulariomodel:  FormularioModel)
}