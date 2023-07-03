package pl.tofix.ipcalc2.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.tofix.ipcalc2.Screen
import pl.tofix.ipcalc2.*

@Composable
fun EntrIPScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var textValue by remember { mutableStateOf("") }
            var textValue2 by remember { mutableStateOf("") }
            var isValidIp by remember { mutableStateOf(false) }
            var isValidNetmask by remember { mutableStateOf(false) }

            TextField(
                value = textValue,
                onValueChange = {
                    textValue = it
                    isValidIp = isValidIpAddress(it)
                },
                label = { Text("Podaj IP (xxx.xxx.xxx.xxx) ") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = !isValidIp
            )

            TextField(
                value = textValue2,
                onValueChange = {
                    textValue2 = it
                    isValidNetmask = isValidNetmaskAddress(it)
                },
                label = { Text("Podaj maskę (xxx.xxx.xxx.xxx)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = !isValidNetmask
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = { navController.popBackStack() }
                ) {
                    Text(text = "Cofnij")
                }

                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        if (isValidIp && isValidNetmask) {
                            navController.navigate(
                                route = Screen.Calced.passIpAndMask(
                                    textValue,
                                    textValue2
                                )
                            )
                        }
                    },
                    enabled = isValidIp && isValidNetmask
                ) {
                    Text(text = "Oblicz")
                }
            }
        }
    }
}

