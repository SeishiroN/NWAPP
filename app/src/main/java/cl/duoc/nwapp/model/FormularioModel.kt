package cl.duoc.nwapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personas")
data class FormularioModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nombre: String,
    var correo: String,
    var edad: Int,
    val terminos: Boolean
)