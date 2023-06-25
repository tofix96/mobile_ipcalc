package pl.tofix.ipcalc2.ui.theme.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import pl.tofix.ipcalc2.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Witaj w kalkulatorze IP")
            Button(onClick = { navController.navigate(route = Screen.EntrIP.route) }) {
                Text(text = "Podaj IP")
            }
            Button(onClick = { navController.navigate(route = Screen.Check.route) }) {
                Text(text = "Sprawdź moje IP")
            }
        }
    }
}
