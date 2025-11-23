
package cl.duoc.nwapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.nwapp.repository.FormularioRepository
import kotlinx.coroutines.launch

/**
 * --- VIEWMODEL DEFINITIVO ---
 * Simplificado para manejar el login con email y password.
 */
class FormularioViewModel : ViewModel() {
    private val repository = FormularioRepository()

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf("")

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPass: String) {
        password = newPass
    }

    fun realizarLogin(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            val esLoginExitoso = repository.validarLogin(email, password)

            if (esLoginExitoso) {
                errorMessage = ""
                onLoginSuccess()
            } else {
                errorMessage = "Email o Contraseña inválidos."
            }
        }
    }
}
