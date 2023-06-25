package pl.tofix.ipcalc2

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pl.tofix.ipcalc2.ui.theme.screen.CalcedIpScreen
import pl.tofix.ipcalc2.ui.theme.screen.CheckIpScreen
import pl.tofix.ipcalc2.ui.theme.screen.EntrIPScreen
import pl.tofix.ipcalc2.ui.theme.screen.HomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.EntrIP.route,

            ) {

            EntrIPScreen(navController = navController)
        }
        composable(
            route = Screen.Check.route
        ) {
            CheckIpScreen(navController = navController)
        }
        composable(
            route = Screen.Calced.route,
            arguments = listOf(
                navArgument(CALC_ARGUMENT_KEY)
                {
                    type = NavType.StringType
                }, navArgument(CALC_ARGUMENT_KEY2)
                {
                    type = NavType.StringType
                })

        ) {
            val arg1 = it.arguments?.getString(CALC_ARGUMENT_KEY).toString()
            val arg2 = it.arguments?.getString(CALC_ARGUMENT_KEY2).toString()
            CalcedIpScreen(navController = navController, arg1, arg2)
        }
    }
}