package crystal.physionow.ui.theme.pages

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.camera.core.Camera
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeScannerPage() {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scanne dein Dokument hier!") }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                if (hasCameraPermission) {
                    Box(
                        modifier = Modifier
                            .size(500.dp)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "[ Kameraansicht für QR Code Scanner ]",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Bitte erlauben Sie den Kamerazugriff, um den QR Code Scanner zu nutzen.")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }) {
                            Text("Kamerazugriff erlauben")
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.background(Color(0xFFFFFFFF))
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { /* QR-Code Scanvorgang starten */ }) {
                    Text("Scan")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    )
}

//TODO: KAMERAANSICHT IMPLEMENTIEREN
