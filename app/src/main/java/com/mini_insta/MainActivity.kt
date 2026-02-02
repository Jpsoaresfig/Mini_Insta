package com.mini_insta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mini_insta.screens.LoginScreen
import com.mini_insta.screens.MainScreen
import com.mini_insta.screens.PostScreen
import com.mini_insta.screens.RegisterScreen
import com.mini_insta.ui.theme.Mini_instaTheme
import view.AuthViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import android.util.Log





class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Mini_instaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    val loggedUser by authViewModel.loggedUser.collectAsState(initial = null)
                    val errorMessage by authViewModel.errorMessage.collectAsState(initial = null)


                    var currentScreen by remember { mutableStateOf("login") }


                    LaunchedEffect(loggedUser) {
                        if (loggedUser != null) {
                            currentScreen = "main"
                        }
                    }

                    Box(modifier = Modifier.fillMaxSize()) {

                        LaunchedEffect(currentScreen) {
                            Log.d("NAVIGATION", "Tela atual: $currentScreen")
                        }
                        when (currentScreen) {

                            "login" -> LoginScreen(
                                onLogin = { email, password ->
                                    authViewModel.login(email, password)
                                },
                                onGoToRegister = {
                                    currentScreen = "register"
                                },
                                errorMessage = errorMessage
                            )

                            "register" -> RegisterScreen(
                                onRegister = { email, password ->
                                    authViewModel.register(email, password)
                                },
                                onBackToLogin = {
                                    currentScreen = "login"
                                },
                                errorMessage = errorMessage
                            )

                            "main" -> MainScreen(
                                userId = loggedUser?.id ?: 0,
                                email = loggedUser?.email ?: "",
                                onLogout = {
                                    authViewModel.logout()
                                    currentScreen = "login"
                                },
                                onNavigateToPost = {
                                    currentScreen = "post"
                                }
                            )

                            "post" -> PostScreen(
                                currentUser = loggedUser?.email ?: "",
                                onPostCreated = {
                                    currentScreen = "main"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}