package cl.duoc.nwapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.duoc.nwapp.repository.FormularioRepository
import cl.duoc.nwapp.model.FormularioModel
import cl.duoc.nwapp.model.MensajesError
import cl.duoc.nwapp.utils.Validacion
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class FormularioViewModel(private val repository: FormularioRepository) : ViewModel() {

    val validation = Validacion()
//    var formulario: FormularioModel by mutableStateOf( repository.getFormulario() )
    var mensajesError: MensajesError by mutableStateOf( repository.getMensajesError() )

    val nombre = MutableStateFlow("")
    val correo = MutableStateFlow("")
    val edad = MutableStateFlow("")
    val terminos = MutableStateFlow(false)

    var respuesta = mutableStateOf(false)

    fun verificarFormulario(): Boolean {
        return verificarNombre() &&
                verificarCorreo() &&
                verificarEdad() &&
                verificarTerminos()
    }
    fun verificarNombre(): Boolean {
        if (!validation.validacionNombre(nombre.value)) {
            mensajesError.nombre = "El nombre debe ser el de un usuario registradoo"
            return false
        } else {
            mensajesError.nombre = ""
            return true
        }

    }

    fun verificarCorreo(): Boolean {
        if(!validation.validacionCorreo(correo.value)) {
            mensajesError.correo = "El correo no es válido"
            return false
        } else {
            mensajesError.correo = ""
            return true
        }

    }

    fun verificarEdad(): Boolean {
        if(!validation.validacionEdad(edad.value.toInt())) {
            mensajesError.edad = "La edad debe ser un número entre 0 y 120"
            return false
        } else {
            mensajesError.edad = ""
            return true
        }

    }

    fun verificarTerminos(): Boolean {
        if(!validation.validacionTerminos(terminos.value)) {
            mensajesError.terminos = "Debes aceptar los términos"
            return false
        } else {
            mensajesError.terminos = ""
            return true
        }
    }
    fun login(){
        viewModelScope.launch{
        var respuesta_login= repository.login(nombre.value,correo.value)
            respuesta.value= respuesta_login


        }

    }

}