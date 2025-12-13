package cl.duoc.nwapp.data.remote

import cl.duoc.nwapp.data.model.LoginRequest
import cl.duoc.nwapp.data.model.LoginResponse
import cl.duoc.nwapp.data.model.SignupRequest
import cl.duoc.nwapp.data.model.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): Response<LoginResponse>

    /**
     * Endpoint para crear un nuevo usuario.
     * Usa la ruta relativa que se combinará con la base URL de signup.
     */
    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>
}
