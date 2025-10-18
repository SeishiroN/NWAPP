package cl.duoc.nwapp.utils

class Validacion {

fun validacionNombre(nombre:String): Boolean {
        if(nombre !="ADMIN")
            return false
        else
            return true
    }

    fun validacionCorreo(correo:String): Boolean {
        if (!correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$")))
            return false
        else
            return true
    }

    fun validacionEdad(edad:Int): Boolean {
        if (edad < 0 || edad > 120)
            return false
        else
            return true
    }

    fun validacionTerminos(terminos:Boolean): Boolean {
        if (!terminos)
            return false
        else
            return true
    }
}