package cl.duoc.nwapp.model

data class UsuarioFormulario(
    var nombre: String = "",
    var correo: String = "",
    var edad: String = "",
    var terminos: Boolean = false
)