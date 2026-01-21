package view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.insta.api.ApiService
import data.insta.model.UserRequest
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                Log.d("LOGIN", "Chamando API")

                val response = ApiService.authApi.login(
                    UserRequest(email, password)
                )

                if (response.isSuccessful) {
                    Log.d("LOGIN", "TOKEN: ${response.body()?.token}")
                } else {
                    Log.e("LOGIN", "Erro HTTP: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.e("LOGIN", "Exception", e)
            }
        }
    }
}
