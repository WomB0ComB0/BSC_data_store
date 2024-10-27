package edu.farmingdale.datastoresimplestoredemo
import android.content.Context
import java.io.PrintWriter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import edu.farmingdale.datastoresimplestoredemo.data.AppPreferences
import edu.farmingdale.datastoresimplestoredemo.ui.theme.DataStoreSimpleStoreDemoTheme
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataStoreSimpleStoreDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DataStoreDemo(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DataStoreDemo(modifier: Modifier) {
    val store = AppStorage(LocalContext.current)
    val appPrefs = store.appPreferenceFlow.collectAsState(AppPreferences())
    val coroutineScope = rememberCoroutineScope()
    var username by remember { mutableStateOf("") }
    var highScore by remember { mutableStateOf("0") }

    Column(modifier = modifier.padding(16.dp)) {
        Text("Current Values:")
        Text("Username: ${appPrefs.value.userName}")
        Text("High Score: ${appPrefs.value.highScore}")
        Text("Dark Mode: ${appPrefs.value.darkMode}")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Enter Username") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = highScore,
            onValueChange = { highScore = it },
            label = { Text("Enter High Score") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = {
                coroutineScope.launch {
                    store.saveUsername(username)
                    store.saveHighScore(highScore.toIntOrNull() ?: 0)
                }
            }) {
                Text("Save Values")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                coroutineScope.launch {
                    store.saveDarkMode(!appPrefs.value.darkMode)
                }
            }) {
                Text("Toggle Dark Mode")
            }
        }
    }
}

// ToDo 1: Modify the App to store a high score and a dark mode preference
// ToDo 2: Modify the APP to store the username through a text field
// ToDo 3: Modify the App to save the username when the button is clicked
// ToDo 4: Modify the App to display the values stored in the DataStore




