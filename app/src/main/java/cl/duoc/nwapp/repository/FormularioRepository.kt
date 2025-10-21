package cl.duoc.nwapp.repository

import cl.duoc.nwapp.model.FormularioModel
import cl.duoc.nwapp.model.MensajesError

class FormularioRepository {
    private var formulario = FormularioModel() // Esto trae el formulario model de ingreso, guardandose en la variable formulario
    private var errores = MensajesError() // Esto trae el formulario model del error en el ingreso, guardandose en la variable

    fun getFormulario(): FormularioModel = formulario // Trae el formulario

    fun getMensajesError(): MensajesError = errores // Trae los errores

    //
    fun cambiarNombre(nuevoNombre: String) {
        formulario.nombre = nuevoNombre
    }

    // Para validar el nombre (En este caso, forzaremos el programa a que solo acepte ADMIN)
    fun validacionNombre(): Boolean {
        if (formulario.nombre != "ADMIN") {
            return false
        } else {
            return true
        }
    }

    // Valida que en el formulario, haya ingresado un string equivalente al patrón indicado en el formulario
    fun validacionCorreo(): Boolean {
        if (!formulario.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))) {
            return false
        } else {
            return true
        }
    }

    // Valida que la edad ingresada sea mayor a 18 (años)
    fun validacionEdad(): Boolean {
        val edadInt = formulario.edad.toIntOrNull()
        if (edadInt == null || edadInt < 0 || edadInt > 120) {
            return false
        } else {
            return true
        }
    }

    // Valida que la casilla de "Términos" haya sido selccionada.
    fun validacionTerminos(): Boolean {
        if (!formulario.terminos) {
            return false
        } else {
            return true
        }
    }
}
