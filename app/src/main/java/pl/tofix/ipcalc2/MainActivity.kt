package pl.tofix.ipcalc2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.tofix.ipcalc2.ui.theme.IpCalc2Theme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IpCalc2Theme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}


