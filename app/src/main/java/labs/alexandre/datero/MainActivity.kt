package labs.alexandre.datero

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import labs.alexandre.datero.presentation.root.DateroRootScreen
import labs.alexandre.datero.presentation.navigation.DateroNavigation
import labs.alexandre.datero.presentation.theme.DateroTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            AppDatero()
        }
    }

}

@Composable
fun AppDatero() {
    DateroTheme {
        DateroRootScreen {
            DateroNavigation()
        }
    }
}