package cz.kiec.idontwanttosee

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import cz.kiec.idontwanttosee.ui.Application
import cz.kiec.idontwanttosee.ui.theme.IDontWantToSeeTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheckPermission()
            IDontWantToSeeTheme {
                KoinContext {
                    Application()
                }
            }
        }
    }
}

@Composable
private fun CheckPermission() {
    if (NotificationManagerCompat.getEnabledListenerPackages(LocalContext.current)
            .contains(LocalContext.current.packageName)
    ) return

    Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS).also {
        LocalContext.current.startActivity(it)
    }
}