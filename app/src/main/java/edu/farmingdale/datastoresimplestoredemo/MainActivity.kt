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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.TextStyle

class MainActivity : ComponentActivity() {
    private lateinit var store: AppStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = AppStorage(this)
        
        enableEdgeToEdge()
        setContent {
            val appPrefs = store.appPreferenceFlow.collectAsState(AppPreferences())
            
            DataStoreSimpleStoreDemoTheme(
                darkTheme = appPrefs.value.darkMode
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DataStoreDemo(
                        modifier = Modifier.padding(innerPadding),
                        store = store
                    )
                }
            }
        }
    }
}

@Composable
fun DataStoreDemo(
    modifier: Modifier,
    store: AppStorage
) {
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
            onValueChange = { newValue: String -> 
                try {
                    username = newValue
                } catch (e: Exception) {
                    Log.e("DataStoreDemo", "Error updating username", e)
                }
            },
            label = { Text("Enter Username") },
            textStyle = TextStyle.Default,
            colors = TextFieldDefaults.colors()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = highScore,
            onValueChange = { newValue: String -> 
                try {
                    highScore = newValue
                } catch (e: Exception) {
                    Log.e("DataStoreDemo", "Error updating high score", e)
                }
            },
            label = { Text("Enter High Score") },
            textStyle = TextStyle.Default,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = {
                coroutineScope.launch {
                    try {
                        store.saveUsername(username)
                        store.saveHighScore(highScore.toIntOrNull() ?: 0)
                        Log.d("DataStoreDemo", "Values saved successfully")
                    } catch (e: Exception) {
                        Log.e("DataStoreDemo", "Error saving values", e)
                    }
                }
            }) {
                Text("Save Values")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                coroutineScope.launch {
                    try {
                        val newDarkMode = !appPrefs.value.darkMode
                        store.saveDarkMode(newDarkMode)
                        Log.d("DataStoreDemo", "Dark mode toggled to: $newDarkMode")
                    } catch (e: Exception) {
                        Log.e("DataStoreDemo", "Error toggling dark mode", e)
                    }
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




