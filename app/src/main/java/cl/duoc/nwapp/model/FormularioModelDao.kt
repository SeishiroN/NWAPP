package cl.duoc.nwapp.model

import androidx.room.*

@Dao
interface FormularioModelDao {
    @Query("SELECT * FROM personas LIMIT 3")
    suspend fun getAll(): List<FormularioModel>
    @Query("SELECT * FROM personas where nombre = :nombre and correo = :correo")
    suspend fun login(nombre:String, correo:String): FormularioModel

    @Insert
    suspend fun insert(formulario: FormularioModel)

    @Update
    suspend fun update(formulario: FormularioModel)

    @Delete
    suspend fun delete(formulario: FormularioModel)
}

