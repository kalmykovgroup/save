package group.kalmykov.safe.models


sealed class Routes(val route: String) {
    object Home : Routes("home_screen")
    object Pass : Routes("pass_screen")
}
