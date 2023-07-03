package pl.tofix.ipcalc2.ui.theme.screen

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.tofix.ipcalc2.*


@Composable
fun CalcedIpScreen(
    navController: NavController,
    ipAddress: String,
    subnetMask: String
) {
    val activity = (LocalContext.current as? Activity)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Twoje IP to: $ipAddress")
            Text(text = "Twoja maska to: $subnetMask")
            Text(text = "Adres sieci to: ${calculateNetworkAddress(ipAddress, subnetMask)}")
            Text(text = "Adres rozgłoszeniowy to: ${calculateBroadcast(ipAddress, subnetMask)}")
            Text(text = "Ilość hostów w sieci: ${calculateAvailableHosts(subnetMask)}")
            Text(text = "Host min to: ${calculateMinHost(ipAddress, subnetMask)}")
            Text(text = "Host max to: ${calculateMaxHost(ipAddress, subnetMask)}")
            Text(text = "Maksymalna ilość podsieci to: ${calculateMaxSubnets(subnetMask)}")
            Button(
                modifier = Modifier.padding(4.dp),
                onClick = { navController.popBackStack() }
            ) {
                Text(text = "Cofnij")
            }

            Button(onClick = {
                activity?.finish()
            }) {
                Text("Zamknij")
            }
        }
    }
}
