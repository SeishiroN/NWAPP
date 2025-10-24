package cl.duoc.nwapp.model

import androidx.room.Entity // Anotación que marca una clase como una tabla de la base de datos Room.
import androidx.room.PrimaryKey // Anotación que designa una propiedad como la clave primaria de la tabla.

/**
 * `data class` que representa la entidad de nuestra tabla en la base de datos.
 * Cada instancia de esta clase corresponde a una fila en la tabla "datos".
 * @param tableName El nombre de la tabla en la base de datos SQLite.
 */
@Entity(tableName = "datos")
data class Datos(
    /**
     * La clave primaria de la tabla. Es un campo único para cada fila.
     * @param autoGenerate `true` le dice a Room que genere automáticamente un valor incremental
     * para esta columna cada vez que se inserta un nuevo `Datos` sin un ID especificado.
     */
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Las demás propiedades de la clase se convierten en columnas en la tabla "datos".
    val nombre: String,
    val latitud: String,
    val longitud: String,
)
