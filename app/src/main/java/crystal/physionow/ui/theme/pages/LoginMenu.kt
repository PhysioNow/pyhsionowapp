package crystal.physionow.ui.theme.pages

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import crystal.physionow.ui.theme.PhysionowTheme

class LoginPage : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhysionowTheme {
                LoginPageUI(this)
            }
        }
    }

    @Composable
    fun LoginPageUI(context: Context) {
        val username = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val isLogin = remember { mutableStateOf(true) }
        val buttonText = if (isLogin.value) "Login" else "Register"

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (isLogin.value) {
                        login(username.value, password.value, context)
                    } else {
                        register(username.value, password.value, context)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(buttonText)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { isLogin.value = !isLogin.value }) {
                Text(if (isLogin.value) "Don't have an account? Register" else "Already have an account? Login")
            }
        }
    }

    private fun login(username: String, password: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val storedUsername = sharedPreferences.getString("username", null)
        val storedPassword = sharedPreferences.getString("password", null)

        if (storedUsername == username && storedPassword == password) {
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun register(username: String, password: String, context: Context) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username", username)
            editor.putString("password", password)
            editor.apply()

            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Please fill in both fields", Toast.LENGTH_SHORT).show()
        }
    }
}
