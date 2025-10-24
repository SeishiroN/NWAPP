package cl.duoc.nwapp.model

import androidx.room.Database // Anotación que define una clase como una base de datos Room.
import androidx.room.RoomDatabase // Clase base para las bases de datos de Room.

/**
 * Clase abstracta que representa la base de datos de la aplicación.
 * Hereda de `RoomDatabase`, que le proporciona la funcionalidad subyacente.
 *
 * @param entities Un array de todas las clases de entidad que pertenecen a esta base de datos.
 *                 En este caso, solo tenemos la tabla `Datos`.
 * @param version El número de versión de la base de datos. Si cambias el esquema (ej: añades
 *                una columna a `Datos`), DEBES incrementar este número para que Room maneje
 *                la migración. Si no lo haces, la app crasheará.
 */
@Database(entities = [Datos::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Método abstracto que devuelve el DAO para la entidad `Datos`.
     * Room generará automáticamente la implementación de este método.
     * A través de `database.datosDao()`, el Repositorio obtendrá acceso a las operaciones
     * de la base de datos (INSERT, SELECT, etc.).
     */
    abstract fun datosDao(): DatosDao
}
