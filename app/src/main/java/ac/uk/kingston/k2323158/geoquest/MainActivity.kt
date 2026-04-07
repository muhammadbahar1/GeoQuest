package ac.uk.kingston.k2323158.geoquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ac.uk.kingston.k2323158.geoquest.ui.theme.GeoQuestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GeoQuestTheme {
                LandingScreen(
                    onModeSelected = {
                        // TODO: handle navigation later
                    }
                )
            }
        }
    }
}