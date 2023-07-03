package pl.tofix.ipcalc2.ui.theme.screen

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pl.tofix.ipcalc2.Screen



@Composable
fun HomeScreen(navController: NavController) {
    val activity = (LocalContext.current as? Activity)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                text = "Witaj w kalkulatorze IP",
                modifier = Modifier.padding(vertical = 50.dp),
                color = Color.White,
                fontSize = 36.sp,
            )
            Button(
                onClick = { navController.navigate(route = Screen.EntrIP.route) },
                modifier = Modifier.padding(top = 200.dp)
            ) {
                Text(text = "Podaj IP")
            }
            Button(
                onClick = { navController.navigate(route = Screen.Check.route) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Sprawdź moje IP")
            }
            Button(onClick = {
                activity?.finish()
            }) {
                Text("Zamknij")
            }
        }
    }
}
