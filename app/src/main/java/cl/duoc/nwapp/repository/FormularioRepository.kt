package cl.duoc.nwapp.repository

import cl.duoc.nwapp.model.FormularioModel
import cl.duoc.nwapp.model.MensajesError

// logica del repositorio
class  FormularioRepository {


    private var formulario = FormularioModel()
    private var errores = MensajesError()


    fun getFormulario():  FormularioModel = formulario
    fun getMensajesError():  MensajesError = errores



    // verifica si el nombre es igual a la valor que se le introdujo, validación con datos en bruto
    fun validacionNombre(): Boolean {

        return formulario.nombre == "ADMIN"
    }

    //validación de formato de correo según patrón indicado
    fun validacionCorreo(): Boolean {

        return formulario.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
    }

    //validación de rango de edad
    fun validacionEdad(): Boolean {

        val edadInt = formulario.edad.toIntOrNull()
        return edadInt != null && edadInt >= 18 && edadInt <= 120
    }


    //validación si tiene el check puesto
    fun validacionTerminos(): Boolean {
        return formulario.terminos
    }
}