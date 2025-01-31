package crystal.physionow.ui.theme.devpages

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import crystal.physionow.R

@SuppressLint("RememberReturnType")
@Composable
fun DevSettingsPage() {
    val context = LocalContext.current

    remember { createNotificationChannel(context) }

    val notificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showTestNotification(context)
        }
    }

    Button(onClick = {
        handleNotificationPermission(context, notificationLauncher)
    }) {
        Text("Test Benachrichtigung anzeigen")
    }
}

private fun handleNotificationPermission(
    context: Context,
    launcher: androidx.activity.result.ActivityResultLauncher<String>
) {
    when {
        Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> {
            showTestNotification(context)
        }

        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED -> {
            showTestNotification(context)
        }

        else -> {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "test_channel",
            "Test Benachrichtigungen",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Kanal für Test-Benachrichtigungen"
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

private fun showTestNotification(context: Context) {
    val notificationId = 1
    val notificationBuilder = NotificationCompat.Builder(context, "test_channel")
        .setSmallIcon(R.drawable.physionowlogo)
        .setContentTitle("Test Benachrichtigung")
        .setContentText("Das ist eine Test-Benachrichtigung")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        NotificationManagerCompat.from(context)
            .notify(notificationId, notificationBuilder.build())
    }
}