package cl.duoc.nwapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "datos")
data class Datos(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val latiud: String,
    val longitud: String
)