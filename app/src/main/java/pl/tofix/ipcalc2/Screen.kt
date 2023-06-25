package pl.tofix.ipcalc2


const val CALC_ARGUMENT_KEY = "ip"
const val CALC_ARGUMENT_KEY2 = "mask"


sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object EntrIP : Screen(route = "entrIPScreen")
    object Check : Screen(route = "checkipscreen")
    object Calced : Screen(route = "calcedipscreen/{$CALC_ARGUMENT_KEY}/{$CALC_ARGUMENT_KEY2}")


    fun passIpAndMask(
        ip: String,
        mask: String
    ): String {
        return "calcedipscreen/$ip/$mask"
    }
}
