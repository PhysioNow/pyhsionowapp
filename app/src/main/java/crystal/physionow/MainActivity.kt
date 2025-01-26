package crystal.physionow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import crystal.physionow.ui.theme.PhysionowTheme
import crystal.physionow.ui.theme.pages.QRCodeScannerPage
import crystal.physionow.ui.theme.pages.SettingsPage
import crystal.physionow.ui.theme.pages.ImpressumPage
import crystal.physionow.ui.theme.pages.SearchExercisesPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhysionowTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavigationHost(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            Greeting(name = "Android", navController = navController)
        }
        composable("scanner") {
            QRCodeScannerPage()
        }
        composable("settings") {
            SettingsPage(navController)
        }
        composable("impressum") {
            ImpressumPage()  // Impressum-Seite
        }
        composable("searchExercises") {
            SearchExercisesPage()  // Deine neue Seite für die Suche nach Übungen
        }
    }
}

@Composable
fun Greeting(name: String, navController: NavHostController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Setze eine Hintergrundfarbe
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),  // Padding für den Text
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home Icon",
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.secondary // Ansprechendes Icon-Farbdesign
            )
            Spacer(modifier = Modifier.height(16.dp))  // Platz zwischen Icon und Text
            Text(
                text = "Welcome to PhysioNow",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground, // Textfarbe
                modifier = Modifier.padding(bottom = 8.dp)  // Padding für Text
            )
            Button(
                onClick = {
                    navController.navigate("searchExercises")  // Navigiere zur Seite
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Suche nach Übungen")  // Ein einladender Button
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onAccountClick: () -> Unit) {
    TopAppBar(
        title = { Text("PhysioNow") },
        navigationIcon = {
            IconButton(onClick = { onAccountClick() }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Account",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

        },
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Info, contentDescription = "Scanner") },
            label = { Text("Scanner") },
            selected = false,
            onClick = { navController.navigate("scanner") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = false,
            onClick = { navController.navigate("settings") }
        )
    }
}
