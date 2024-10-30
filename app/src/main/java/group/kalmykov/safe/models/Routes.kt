package group.kalmykov.safe.models


sealed class Routes(val route: String) {
    data object Home : Routes("home_screen")
    data object Login : Routes("login_screen")
}
