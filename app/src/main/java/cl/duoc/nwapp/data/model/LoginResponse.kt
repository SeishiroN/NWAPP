
package cl.duoc.nwapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa la respuesta de la API después de un login exitoso.
 */
data class LoginResponse(
    @SerializedName("authToken")
    val authToken: String
)
