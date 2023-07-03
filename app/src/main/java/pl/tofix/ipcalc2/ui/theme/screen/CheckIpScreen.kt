package pl.tofix.ipcalc2.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.tofix.ipcalc2.Screen
import pl.tofix.ipcalc2.*

@Composable
fun CheckIpScreen(navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val result = ipcheck()
            val ipAddress = result?.first ?: "N"
            val subnetMask = result?.second ?: "N"
            val interfaceName = result?.third ?: ""
            if (ipAddress != "N" && subnetMask != "N") {
                Text(text = "Twoje IP to: $ipAddress")
                Text(text = "Twoja maska sieci to: $subnetMask")
                Text(text = "Nazwa interfejsu: $interfaceName")
            } else {
                Text(text = "Nie jesteś podłączony do sieci")
            }

            Row {
                Button(modifier = Modifier.padding(16.dp),
                    onClick = { navController.popBackStack() }) {
                    Text(text = "Cofnij")
                }
                Button(modifier = Modifier.padding(16.dp), onClick = {
                    navController.navigate(
                        route = Screen.Calced.passIpAndMask(
                            ipAddress, subnetMask
                        )
                    )
                }) {
                    Text(text = "Oblicz IP")
                }
            }
        }
    }
}
