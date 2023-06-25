package pl.tofix.ipcalc2.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.tofix.ipcalc2.Screen
import java.net.InetAddress
import java.net.NetworkInterface

@Composable
fun CheckIpScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val result = ipcheck()
            val ipAddress = result?.first ?: "N"
            val subnetMask = result?.second ?: "N"
            println(subnetMask)
            if (ipAddress != "N" && subnetMask != "N") {
                Text(text = "Twoje IP to: $ipAddress")
                Text(text = "Twoja maska sieci to: /$subnetMask")
            } else {
                Text(text = "Nie jesteś podłączony do sieci")
            }

            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    navController.navigate(
                        route = Screen.Calced.passIpAndMask(
                            ipAddress,
                            subnetMask
                        )
                    )
                }
            ) {
                Text(text = "Oblicz IP")
            }
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { navController.popBackStack() }
            ) {
                Text(text = "Cofnij")
            }


        }
    }
}

fun ipcheck(): Pair<String?, String>? {
    try {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()

        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()

            if (networkInterface.isUp) {
                val addresses = networkInterface.interfaceAddresses

                for (address in addresses) {
                    val inetAddress = address.address

                    if (!inetAddress.isLoopbackAddress && inetAddress is InetAddress && inetAddress.hostAddress.indexOf(
                            ':'
                        ) == -1
                    ) {
                        val ipAddress = inetAddress.hostAddress
                        val subnetMask = getSubnetMask(address.networkPrefixLength)
                        return Pair(ipAddress, subnetMask)
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}

fun getSubnetMask(networkPrefixLength: Short): String {
    val maskBits = 0xffffffff shl 32 - networkPrefixLength
    val octets = mutableListOf<String>()

    octets.add((maskBits shr 24 and 0xff).toString())
    octets.add((maskBits shr 16 and 0xff).toString())
    octets.add((maskBits shr 8 and 0xff).toString())
    octets.add((maskBits and 0xff).toString())

    return octets.joinToString(".")
}
