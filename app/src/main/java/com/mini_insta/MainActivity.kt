package com.mini_insta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import com.mini_insta.screens.LoginScreen
import com.mini_insta.screens.MainScreen
import com.mini_insta.ui.theme.Mini_instaTheme
import view.AuthViewModel

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Mini_instaTheme {
                Surface {
                    if (authViewModel.loggedEmail == null) {
                        LoginScreen( { email, password ->
                            authViewModel.login(email, password)
                        },
                        errorMessage = authViewModel.errorMessage)
                    } else {
                        MainScreen(
                            email = authViewModel.loggedEmail!!,
                            onLogout = {
                                authViewModel.logout()
                            }
                        )
                    }
                }
            }
        }
    }
}