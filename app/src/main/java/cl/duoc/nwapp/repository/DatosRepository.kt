
package cl.duoc.nwapp.repository

import cl.duoc.nwapp.model.Datos
import cl.duoc.nwapp.model.DatosDao
import kotlinx.coroutines.flow.Flow

class DatosRepository(private val dao: DatosDao) {

    fun getAll(): Flow<List<Datos>> = dao.getAll()

    suspend fun insert(datos: Datos) {
        dao.insert(datos)
    }

    suspend fun update(datos: Datos) {
        dao.update(datos)
    }

    suspend fun delete(datos: Datos) {
        dao.delete(datos)
    }
}
