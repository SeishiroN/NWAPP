
package cl.duoc.nwapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MensajesError {
    // --- FIX: Se elimina la propiedad `nombre` ---
    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("") 
    var terminos by mutableStateOf("")
}
