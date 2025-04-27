package com.example.chronus_android
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PasswordManagerApp()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PasswordManagerApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "password_list") {
        composable("password_list") {
            PasswordListScreen(
                onAddPassword = { navController.navigate("add_password") }
            )
        }
        composable("add_password") {
            AddPasswordScreen(
                onPasswordAdded = { navController.popBackStack() }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PasswordListScreen(onAddPassword: () -> Unit) {
    val viewModel: PasswordViewModel = viewModel()
    val passwords by viewModel.passwords.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Password Manager", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(passwords) { entry ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Website: ${entry.website}")
                        Text("Username: ${entry.username}")
                        Text("Password: ${viewModel.decryptPassword(entry.encryptedPassword)}")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddPassword) {
            Text("Add Password")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddPasswordScreen(onPasswordAdded: () -> Unit) {
    val viewModel: PasswordViewModel = viewModel()
    var website by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Add New Password", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = website,
            onValueChange = { website = it },
            label = { Text("Website") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (website.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {
                    viewModel.addPassword(website, username, password)
                    onPasswordAdded()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Password")
        }
    }
}