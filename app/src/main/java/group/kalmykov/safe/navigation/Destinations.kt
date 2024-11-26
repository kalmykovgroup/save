package group.kalmykov.safe.navigation

sealed class Destinations(val route: String) {
    data object Home : Destinations("home_screen")
    data object Login : Destinations("login_screen")
    data object FirstLogin : Destinations("first_login_screen")
    data object About  : Destinations("introduction_screen")
    data object CreatePassword  : Destinations("create_password_screen")
}
