package cl.duoc.nwapp.model


//getValue          -> para leer el valor
//setValue          -> para asignar un valor
//mutableStateOf    -> para poder mutar el valor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// Este es la estructura de datos del formulario

class FormularioModel {



    var nombre by mutableStateOf("")

    var correo by mutableStateOf("")

    var edad by mutableStateOf("")

    var terminos by mutableStateOf(false)
}



