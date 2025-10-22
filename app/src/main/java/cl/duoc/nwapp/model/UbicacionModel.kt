package cl.duoc.nwapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ubicaciones")
data class UbicacionGuardada(
    
    // `@PrimaryKey` para demostrar que es una clave primaria
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // El ID es un entero y su valor por defecto es 0

    // `@ColumnInfo` permite darle un nombre específico a la columna en la base de datos.
    @ColumnInfo(name = "nombre_lugar")
    val nombre: String,

    @ColumnInfo(name = "latitud_coord")
    val latitud: Double,

    @ColumnInfo(name = "longitud_coord")
    val longitud: Double
)
