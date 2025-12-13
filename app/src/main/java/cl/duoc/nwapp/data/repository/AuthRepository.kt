package cl.duoc.nwapp.data.repository

import cl.duoc.nwapp.data.model.SignupRequest
import cl.duoc.nwapp.data.model.SignupResponse
import cl.duoc.nwapp.data.remote.ApiService

class AuthRepository(private val apiService: ApiService) {
    suspend fun signup(request: SignupRequest): SignupResponse {
        val response = apiService.signup(request)

        if (response.isSuccessful) {
            // Si la respuesta es exitosa (código 2xx), devolvemos el cuerpo de la respuesta.
            // Usamos un 'throw' para el caso de que el cuerpo sea nulo, asegurando que no devolvemos nulos.
            return response.body() ?: throw IllegalStateException("El cuerpo de la respuesta es nulo")
        } else {
            // Si la respuesta no fue exitosa, lanzamos una excepción con el código de error.
            // El ViewModel la atrapará y mostrará el error al usuario.
            throw Exception("Falló el registro: Código ${response.code()}")
        }
    }
}
