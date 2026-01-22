package com.mini_insta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mini_insta.screens.LoginScreen
import com.mini_insta.screens.MainScreen
import com.mini_insta.screens.RegisterScreen
import com.mini_insta.ui.theme.Mini_instaTheme
import view.AuthViewModel
import androidx.compose.runtime.collectAsState


class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Mini_instaTheme {
                Surface {

                    val loggedEmail by authViewModel.loggedEmail.collectAsState()
                    val errorMessage by authViewModel.errorMessage.collectAsState()
                    var showRegister by remember { mutableStateOf(false) }

                    when {
                        loggedEmail != null -> {
                            MainScreen(
                                email = loggedEmail!! as String,
                                onLogout = {
                                    authViewModel.logout()
                                }
                            )
                        }

                        showRegister -> {
                            RegisterScreen(
                                onRegister = { email, password ->
                                    authViewModel.register(email, password)
                                },
                                onBackToLogin = {
                                    showRegister = false
                                },
                                errorMessage = errorMessage
                            )
                        }

                        else -> {
                            LoginScreen(
                                onLogin = { email, password ->
                                    authViewModel.login(email, password)
                                },
                                onGoToRegister = {
                                    showRegister = true
                                },
                                errorMessage = errorMessage
                            )
                        }
                    }
                }
            }
        }
    }
}