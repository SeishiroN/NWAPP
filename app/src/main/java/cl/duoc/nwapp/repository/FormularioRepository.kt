package cl.duoc.nwapp.repository

import cl.duoc.nwapp.model.FormularioModel
import cl.duoc.nwapp.model.FormularioModelDao
import cl.duoc.nwapp.model.MensajesError


class  FormularioRepository(private val dao: FormularioModelDao) {


    private var errores = MensajesError()

   fun getMensajesError():  MensajesError = errores

    suspend fun registrar(nombre:String,correo:String,edad:Int,terminos:Boolean){
        val form = FormularioModel(nombre=nombre, correo = correo, edad=edad, terminos=terminos)
        this.dao.insert(form)
    }

    suspend fun login(nombre: String,correo: String): Boolean{
        var form =  this.dao.login(nombre=nombre,correo=correo)
        return !form.nombre.isNullOrEmpty()
    }





    }




