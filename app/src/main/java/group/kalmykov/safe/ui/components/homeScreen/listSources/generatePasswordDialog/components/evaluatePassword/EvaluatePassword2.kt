package group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.components.evaluatePassword

import kotlin.math.ln


fun EvaluatePassword2(password: String): Float {

    if (password.isEmpty()) return 0f

    // Определяем веса для каждой категории
    val lengthScore = (password.length.coerceIn(4, 64) - 4)// Длина от 8 до 20 символов

    // val lengthScore = (password.length.coerceIn(8, 64) - 8) / 12f // Длина от 8 до 20 символов


    val lowercaseScore = (if (password.any { it.isLowerCase() }) 0.01f else 0f) * lengthScore
    val uppercaseScore = if (password.any { it.isUpperCase() }) 0.01f else 0f * lengthScore
    val digitScore = if (password.any { it.isDigit() }) 0.01f else 0f * lengthScore
    val specialCharScore = if (password.any { it in "!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~" }) 0.01f else 0f * lengthScore
    val entropyScore = calculateEntropy2(password) // Оценка энтропии

    // Получаем общую оценку (обрезаем до 1f)
    val totalScore = (lowercaseScore + uppercaseScore + digitScore + specialCharScore + entropyScore)
        .coerceIn(0f, 1f)

    return totalScore
}

// Метод для вычисления энтропии пароля
fun calculateEntropy2(password: String): Float {
    // Получаем количество уникальных символов
    val uniqueCharsCount = password.toSet().size
    val passwordLength = password.length

    // Если пароль состоит из одного символа или его длина меньше 2, энтропия равна 0
    if (uniqueCharsCount <= 1 || passwordLength < 4) {
        return 0f
    }

    // Величина энтропии пропорциональна количеству уникальных символов
    val entropy = (ln(uniqueCharsCount.toFloat()) / ln(2.0f)) * passwordLength

    // Преобразуем энтропию в диапазон от 0 до 1, используя логарифм
    val normalizedEntropy = entropy / (ln(94.0f) / ln(2.0f) * passwordLength) // 94 - это количество символов в стандартном ASCII диапазоне

    return normalizedEntropy.coerceIn(0.0f, 1.0f)
}