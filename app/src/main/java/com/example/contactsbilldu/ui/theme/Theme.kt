import android.R.color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private val BillduBlue = Color(0xFF2B75BC)
private val BillduGreen = Color(0xFF25A36E)
private val BillduDarkBlue = Color(0xFF265B97)
private val BillduDarkGreen = Color(0xFF1F7D4D)
private val BillduLightBackground = Color(0xFFFFFFFF)
private val BillduDarkBackground = Color(0xFF121212)
private val BillduSurface = Color(0xFFFFFFFF)
private val BillduOnSurface = Color(0xFF000000)
private val BillduError = Color(0xFFB00020)
private val BillduOnError = Color(0xFFFFFFFF)

// Define additional colors
private val BillduTertiary = Color(0xFFFFC107)
private val BillduOnTertiary = Color(0xFF000000)

val LightColors = lightColorScheme(
    primary = BillduBlue,
    onPrimary = Color.White,
    secondary = BillduGreen,
    onSecondary = Color.White,
    tertiary = BillduTertiary,
    onTertiary = BillduOnTertiary,
    background = BillduLightBackground,
    onBackground = Color.Black,
    surface = BillduSurface,
    onSurface = BillduOnSurface,
    error = BillduError,
    onError = BillduOnError,
)

val DarkColors = darkColorScheme(
    primary = BillduBlue,
    onPrimary = Color.White,
    secondary = BillduGreen,
    onSecondary = Color.White,
    tertiary = BillduTertiary,
    onTertiary = BillduOnTertiary,
    background = BillduDarkBackground,
    onBackground = Color.White,
    surface = BillduDarkBackground,
    onSurface = Color.White,
    error = BillduError,
    onError = BillduOnError,
)

@Composable
fun BillduTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) DarkColors else LightColors

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = !darkTheme
        )
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}