package ac.uk.kingston.k2323158.geoquest.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import ac.uk.kingston.k2323158.geoquest.ui.theme.*

data class NavBarItem(
    val label: String,
    val icon: ImageVector
)

val navBarItems = listOf(
    NavBarItem("Map", Icons.Default.Map),
    NavBarItem("Alerts", Icons.Default.Notifications),
    NavBarItem("Ranks", Icons.Default.EmojiEvents),
    NavBarItem("Profile", Icons.Default.Person)
)

@Composable
fun BottomNavBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(containerColor = DarkForestGreen) {
        navBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = WhiteText
                    )
                },
                label = { Text(item.label, color = WhiteText) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = DarkForestGreen.copy(alpha = 0.3f)
                )
            )
        }
    }
}