package com.mini_insta


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            var loggedEmail by remember { mutableStateOf<String?>(null) }

            Mini_instaTheme {
                Surface {
                    if (loggedEmail == null) {
                        LoginScreen { email, password ->
                            loggedEmail = email
                        }
                    } else {
                        MainScreen(email = loggedEmail!!)
                    }
                }
            }
        }

    }
}
