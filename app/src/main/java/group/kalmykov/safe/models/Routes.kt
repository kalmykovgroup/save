package group.kalmykov.safe.models


sealed class Routes(val route: String) {
    data object Home : Routes("home_screen")
    data object Login : Routes("login_screen")
    data object Welcome : Routes("welcome_screen")
    data object Introduction  : Routes("introduction_screen")
    data object CreatePinCode  : Routes("create_pin_code_screen")
    data object DestinationRoute  : Routes("destination_route")
}
