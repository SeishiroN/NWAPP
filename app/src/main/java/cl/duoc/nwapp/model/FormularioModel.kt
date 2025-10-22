package cl.duoc.nwapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FormularioModel {


// clase que tiene los datos que se tienen que ingresar al formulario
    var nombre by mutableStateOf("")


    var correo by mutableStateOf("")


    var edad by mutableStateOf("")


    var terminos by mutableStateOf(false)
}