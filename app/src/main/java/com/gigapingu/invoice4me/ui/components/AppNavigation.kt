import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gigapingu.invoice4me.ui.theme.GlassWhite20
import com.gigapingu.invoice4me.ui.theme.GlassWhite25
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme

data class AppNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val items = listOf(
    AppNavItem("Home", Icons.Filled.Home, "home"),
    AppNavItem("Add", Icons.Filled.Add, "favorites"),
    AppNavItem("Settings", Icons.Filled.Settings, "settings")
)

@Composable
fun AppNavigation() {
    var selectedItemIndex by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = GlassWhite25
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    // navController.navigate(item.route) // Uncomment and use with NavHost
                },
                label = { Text(item.label) },
                icon = { Icon(item.icon, contentDescription = item.label) }
            )
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview() {
    Invoice4MeTheme() {
    AppNavigation()
    }
}
