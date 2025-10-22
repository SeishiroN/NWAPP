package cl.duoc.nwapp.repository

import cl.duoc.nwapp.model.Datos
import cl.duoc.nwapp.model.DatosDao

// tremendo pendejo mi compañero, se equivoco al copiar (y es negro)
class DatosRepository(
    private val dao: DatosDao,
) {
    suspend fun getAll() = dao.getAll()

    suspend fun insert(datos: Datos) = dao.insert(datos)

    suspend fun update(datos: Datos) = dao.update(datos)

    suspend fun delete(datos: Datos) = dao.delete(datos)
}
