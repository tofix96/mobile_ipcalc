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
import pl.tofix.ipcalc2.validateTextValue

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
            var isTextValueValid by remember { mutableStateOf(true) }

            var textValue2 by remember { mutableStateOf("") }
            var isTextValue2Valid by remember { mutableStateOf(true) }
            TextField(
                value = textValue,
                onValueChange = {
                    textValue = it
                    isTextValueValid = validateTextValue(it)
                },
                label = { Text("Podaj IP w formacie xxx.xxx.xxx.xxx") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = !isTextValueValid
            )
            TextField(
                value = textValue2,
                onValueChange = {
                    textValue2 = it
                    isTextValue2Valid = validateTextValue(it)
                },
                label = { Text("Podaj prefix sieci") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = !isTextValue2Valid
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
                        navController.navigate(
                            route = Screen.Calced.passIpAndMask(
                                textValue,
                                textValue2
                            )
                        )
                    }
                ) {
                    Text(text = "Oblicz")
                }



            }
        }
    }
}
