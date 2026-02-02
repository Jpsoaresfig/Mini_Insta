package view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.insta.api.ApiService
import data.insta.model.TokenManager
import data.insta.model.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import data.insta.model.UserResponse



class AuthViewModel : ViewModel() {

    private val _loggedUser = MutableStateFlow<UserResponse?>(null)
    val loggedUser: StateFlow<UserResponse?> = _loggedUser.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val authResponse = ApiService.authApi.login(
                    UserRequest(email = email,password = password)
                )

                if (!authResponse.isSuccessful) {
                    _errorMessage.value = "Email ou senha inválidos"
                    return@launch
                }

                TokenManager.token = authResponse.body()!!.token

                val userResponse = ApiService.authApi.getMe(
                    "Bearer ${TokenManager.token}"
                )

                if (userResponse.isSuccessful) {
                    _loggedUser.value = userResponse.body()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Erro ao buscar usuário"
                }

            } catch (e: Exception) {
                _errorMessage.value = "Erro de conexão"
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {

                val response = ApiService.authApi.register(
                    UserRequest(name = name, email = email, password = password)
                )

                if (response.isSuccessful) {
                    Log.d("REGISTER", "User created")
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Erro ao criar conta"
                }

            } catch (e: Exception) {
                _errorMessage.value = "Erro de conexão"
                Log.e("REGISTER", "Erro: ${e.message}")
            }
        }
    }

    fun logout() {
        TokenManager.token = null
        _loggedUser.value = null
        _errorMessage.value = null
    }
}
