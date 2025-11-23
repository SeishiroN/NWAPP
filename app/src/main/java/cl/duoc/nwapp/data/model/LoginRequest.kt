
package cl.duoc.nwapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * --- MODELO DE PETICIÓN DEFINITIVO ---
 * Representa el cuerpo de la petición POST, usando solo los campos que la API espera: email y password.
 */
data class LoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)
