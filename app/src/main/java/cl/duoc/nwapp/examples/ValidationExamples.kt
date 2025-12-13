package cl.duoc.nwapp.examples

import cl.duoc.nwapp.utils.ValidationUtils

/**
 * EJEMPLOS DE USO: Cómo integrar ValidationUtils en tu código
 *
 * Este archivo contiene ejemplos de cómo usar las validaciones
 * en tus ViewModels y Composables.
 */

// ============================================================
// EJEMPLO 1: Validación en ViewModel
// ============================================================

/*
class SignupViewModel(private val repository: AuthRepository) : ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    // Mensajes de error dinámicos
    var nameError by mutableStateOf<String?>(null)
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    fun onNameChange(newName: String) {
        name = newName
        // Validar en tiempo real
        nameError = ValidationUtils.getNameErrorMessage(newName)
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailError = ValidationUtils.getEmailErrorMessage(newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        passwordError = ValidationUtils.getPasswordErrorMessage(newPassword)
    }

    fun performSignup(onSignupSuccess: () -> Unit) {
        // Validar antes de enviar
        if (!validateForm()) {
            return
        }

        isLoading = true
        signupError = null
        viewModelScope.launch {
            try {
                val request = SignupRequest(name, email, password)
                repository.signup(request)
                onSignupSuccess()
            } catch (e: Exception) {
                signupError = e.message ?: "Ocurrió un error desconocido"
            } finally {
                isLoading = false
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (!ValidationUtils.isNameValid(name)) {
            nameError = "El nombre debe tener al menos 2 caracteres"
            isValid = false
        }

        if (!ValidationUtils.isEmailValid(email)) {
            emailError = "Email inválido"
            isValid = false
        }

        if (!ValidationUtils.isPasswordValid(password)) {
            passwordError = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        }

        return isValid
    }
}
*/

// ============================================================
// EJEMPLO 2: Validación en Composable
// ============================================================

/*
@Composable
fun SignupScreen(viewModel: SignupViewModel) {
    Column {
        // Campo de nombre
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Nombre") },
            isError = viewModel.nameError != null,
            supportingText = {
                viewModel.nameError?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        // Campo de email
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") },
            isError = viewModel.emailError != null,
            supportingText = {
                viewModel.emailError?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        // Campo de contraseña
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = viewModel.passwordError != null,
            supportingText = {
                viewModel.passwordError?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        // Botón de registro
        Button(
            onClick = { viewModel.performSignup { /* navegar */ } },
            enabled = viewModel.nameError == null &&
                     viewModel.emailError == null &&
                     viewModel.passwordError == null &&
                     !viewModel.isLoading
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Registrarse")
            }
        }
    }
}
*/

// ============================================================
// EJEMPLO 3: Validación de coordenadas
// ============================================================

/*
class DatosViewModel(private val repository: DatosRepository) : ViewModel() {

    var nombre by mutableStateOf("")
    var latitud by mutableStateOf("")
    var longitud by mutableStateOf("")

    var coordenadasError by mutableStateOf<String?>(null)

    fun onLatitudChange(newLat: String) {
        latitud = newLat
        validateCoordinates()
    }

    fun onLongitudChange(newLon: String) {
        longitud = newLon
        validateCoordinates()
    }

    private fun validateCoordinates() {
        coordenadasError = if (latitud.isNotEmpty() && longitud.isNotEmpty()) {
            if (ValidationUtils.areCoordinatesValid(latitud, longitud)) {
                null
            } else {
                "Coordenadas inválidas"
            }
        } else {
            null
        }
    }

    fun guardarDatos() {
        if (nombre.isBlank()) {
            // Mostrar error
            return
        }

        if (!ValidationUtils.areCoordinatesValid(latitud, longitud)) {
            coordenadasError = "Las coordenadas deben estar en rango válido"
            return
        }

        viewModelScope.launch {
            val datos = Datos(
                nombre = nombre,
                latitud = latitud,
                longitud = longitud
            )
            repository.insert(datos)
        }
    }
}
*/

// ============================================================
// EJEMPLO 4: Validación simple con resultado booleano
// ============================================================

/*
fun validarFormularioSimple(email: String, password: String): Boolean {
    return ValidationUtils.isEmailValid(email) &&
           ValidationUtils.isPasswordValid(password)
}
*/

// ============================================================
// EJEMPLO 5: Mostrar todos los errores a la vez
// ============================================================

/*
fun obtenerTodosLosErrores(
    name: String,
    email: String,
    password: String
): List<String> {
    val errores = mutableListOf<String>()

    ValidationUtils.getNameErrorMessage(name)?.let { errores.add(it) }
    ValidationUtils.getEmailErrorMessage(email)?.let { errores.add(it) }
    ValidationUtils.getPasswordErrorMessage(password)?.let { errores.add(it) }

    return errores
}
*/
