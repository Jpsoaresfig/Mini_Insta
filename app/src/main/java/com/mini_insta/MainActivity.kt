package com.mini_insta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.mini_insta.screens.LoginScreen
import com.mini_insta.ui.theme.Mini_instaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Mini_instaTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    LoginScreen { email, password ->
                        // Aqui depois você chama sua API Node
                        println("Email: $email")
                        println("Senha: $password")
                    }
                }
            }
        }
    }
}
