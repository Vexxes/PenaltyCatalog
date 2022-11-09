package de.vexxes.penaltycatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import de.vexxes.penaltycatalog.navigation.MainScreen
import androidx.navigation.compose.rememberNavController
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val playerViewModel: PlayerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PenaltyCatalogTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController, playerViewModel = playerViewModel)
            }
        }
    }
}