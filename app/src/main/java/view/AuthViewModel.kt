package view

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.insta.api.ApiService
import data.insta.model.UserRequest
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var loggedEmail by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                errorMessage = null

                val response = ApiService.authApi.login(
                    UserRequest(email, password)
                )

                if (response.isSuccessful) {
                    loggedEmail = email
                } else {
                    errorMessage = "Email ou senha incorretos"
                }

            } catch (e: Exception) {
                errorMessage = "Erro de conexão"
            }
        }
    }

    fun logout() {
        loggedEmail = null
        errorMessage = null
    }
}
