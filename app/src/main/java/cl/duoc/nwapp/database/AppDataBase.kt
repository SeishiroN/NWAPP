
package cl.duoc.nwapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.duoc.nwapp.dao.UbicacionDao
import cl.duoc.nwapp.model.UbicacionGuardada

/**
 * La clase principal de la base de datos de la aplicación.
 * 
 * @Database: Anotación que define la configuración de la base de datos.
 *   - `entities`: Un array con todas las clases que son tablas (Entidades).
 *   - `version`: La versión de la base de datos. Si cambias el esquema (ej. añades una columna),
 *     debes incrementar este número.
 *   - `exportSchema`: Es una buena práctica dejarlo en `false` para proyectos simples.
 */
@Database(entities = [UbicacionGuardada::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Una función abstracta que Room implementará por nosotros.
     * Devuelve una instancia de nuestro DAO para que podamos usarlo.
     */
    abstract fun ubicacionDao(): UbicacionDao

    /**
     * El `companion object` es similar a los miembros estáticos en otros lenguajes.
     * Nos permite tener una única instancia de la base de datos para toda la app (patrón Singleton).
     */
    companion object {
        // `@Volatile` asegura que el valor de INSTANCE sea siempre el más actualizado
        // y visible para todos los hilos de ejecución.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Función para obtener la instancia única de la base de datos.
         * Si la instancia no existe, la crea. Si ya existe, la devuelve.
         * Es `synchronized` para evitar que múltiples hilos creen instancias al mismo tiempo.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nwapp_database" // Nombre del archivo de la base de datos
                ).build()
                INSTANCE = instance
                // Devolvemos la instancia recién creada
                instance
            }
        }
    }
}
