package view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.insta.api.ApiService
import data.insta.model.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _loggedEmail = MutableStateFlow<String?>(null)
    val loggedEmail: StateFlow<String?> = _loggedEmail.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null

                val response = ApiService.authApi.login(
                    UserRequest(email, password)
                )

                if (response.isSuccessful) {
                    _loggedEmail.value = email
                } else {
                    _errorMessage.value = "Email ou senha incorretos"
                }

            } catch (e: Exception) {
                _errorMessage.value = "Erro de conexão"
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = ApiService.authApi.register(
                    UserRequest(email, password)
                )

                if (response.isSuccessful) {
                    Log.d("REGISTER", "User created")
                    _errorMessage.value = null // Limpa erro se cadastro der certo
                } else {
                    // Validação de email já existente (supondo que backend retorna 409)
                    if (response.code() == 400) {
                        _errorMessage.value = "Email já existe"
                    } else {
                        _errorMessage.value = "Erro ao criar conta: ${response.code()}"
                    }
                    Log.e("REGISTER", "HTTP Error: ${response.code()}")
                }

            } catch (e: Exception) {
                _errorMessage.value = "Erro de conexão"
                Log.e("REGISTER", "Exception", e)
            }
        }
    }

    fun logout() {
        _loggedEmail.value = null
        _errorMessage.value = null
    }
}
