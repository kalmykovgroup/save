package group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.components.evaluatePassword

fun EvaluatePassword1(password: String): Float {
    if (password.isEmpty()) return 0f

    var score = 0f

    // Проверка длины пароля
    val lengthScore = when {
        password.length >= 12 -> 0.4f
        password.length >= 8 -> 0.3f
        password.length >= 5 -> 0.2f
        else -> 0f
    }

    // Проверка наличия букв
    val hasLowercase = password.any { it.isLowerCase() }
    val hasUppercase = password.any { it.isUpperCase() }
    val letterScore = if (hasLowercase || hasUppercase) 0.2f else 0f

    // Проверка наличия цифр
    val hasDigits = password.any { it.isDigit() }
    val digitScore = if (hasDigits) 0.2f else 0f

    // Проверка наличия специальных символов
    val specialCharacters = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~\\"
    val hasSpecialChar = password.any { it in specialCharacters }
    val specialCharScore = if (hasSpecialChar) 0.2f else 0f

    // Вычисление общей сложности
    score = lengthScore + letterScore + digitScore + specialCharScore

    // Ограничение на максимум 1.0
    return score.coerceIn(0f, 1f)
}