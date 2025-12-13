package cl.duoc.nwapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cl.duoc.nwapp.data.model.SignupRequest
import cl.duoc.nwapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: AuthRepository) : ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var signupError by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)

    fun onNameChange(newName: String) {
        name = newName
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun performSignup(onSignupSuccess: () -> Unit) {
        isLoading = true
        signupError = null
        viewModelScope.launch {
            try {
                val request = SignupRequest(name, email, password)
                repository.signup(request)
                // Si la llamada es exitosa
                onSignupSuccess()
            } catch (e: Exception) {
                signupError = e.message ?: "Ocurrió un error desconocido"
            } finally {
                isLoading = false
            }
        }
    }

    // Factory para crear el ViewModel con sus dependencias
    companion object {
        fun Factory(repository: AuthRepository): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
                    return SignupViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
