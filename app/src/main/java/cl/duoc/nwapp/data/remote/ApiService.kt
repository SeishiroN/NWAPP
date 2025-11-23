
package cl.duoc.nwapp.data.remote

import cl.duoc.nwapp.data.model.LoginRequest
import cl.duoc.nwapp.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    /**
     * --- FIX DEFINITIVO: La ruta completa de la API se mueve aquí ---
     * Esto evita que Retrofit intente parsear la ruta con el ':', que causa el crash.
     */
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): Response<LoginResponse>
}
