package cl.duoc.nwapp.repository

import cl.duoc.nwapp.model.FormularioModel
import cl.duoc.nwapp.model.FormularioModelDao
import cl.duoc.nwapp.model.MensajesError


class  FormularioRepository(private val dao: FormularioModelDao) {

    private var formulario = FormularioModel()
    private var errores = MensajesError()

    fun getFormulario():  FormularioModel = formulario
    fun getMensajesError():  MensajesError = errores

    suspend fun registrar(nombre:String,correo:String,edad:Int,terminos:Boolean){
        val form = FormularioModel(nombre=nombre, correo = correo, edad=edad, terminos=terminos)
        this.dao.insert(form)
    }


    fun cambiarNombre(nuevoNombre: String) {
        formulario.nombre = nuevoNombre
    }

    fun validacionNombre(): Boolean {
        if(formulario.nombre!="ADMIN")
            return false
        else
            return true
    }

    fun validacionCorreo(): Boolean {
        if (!formulario.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$")))
            return false
        else
            return true
    }

    fun validacionEdad(): Boolean {
        val edadInt = formulario.edad.toIntOrNull()
        if (edadInt == null || edadInt < 0 || edadInt > 120)
            return false
        else
            return true
    }

    fun validacionTerminos(): Boolean {
        if (!formulario.terminos)
            return false
        else
            return true
    }






    }




