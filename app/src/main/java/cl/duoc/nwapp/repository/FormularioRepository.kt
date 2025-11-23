
package cl.duoc.nwapp.repository

import cl.duoc.nwapp.data.model.LoginRequest
import cl.duoc.nwapp.data.remote.RetrofitInstance

/**
 * --- REPOSITORIO DEFINITIVO ---
 * Simplificado para usar la nueva y correcta instancia de Retrofit.
 */
class FormularioRepository {
    // La URL completa que causa el problema con Retrofit.
    private val loginUrl = "https://x8ki-letl-twmt.n7.xano.io/api:SzTlK1GN/auth/login"

    suspend fun validarLogin(
        email: String,
        password: String,
    ): Boolean {
        if (email.isBlank() || password.isBlank()) return false

        val request = LoginRequest(email = email, password = password)

        return try {
            // Llama a la función del ApiService pasando la URL completa y la petición.
            val response = RetrofitInstance.api.login(request = request)
            response.isSuccessful
        } catch (t: Throwable) {
            t.printStackTrace()
            false
        }
    }
}
