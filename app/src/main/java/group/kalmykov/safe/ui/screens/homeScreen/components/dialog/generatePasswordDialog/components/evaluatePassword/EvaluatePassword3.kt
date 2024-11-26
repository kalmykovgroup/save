package group.kalmykov.safe.ui.screens.homeScreen.components.dialog.generatePasswordDialog.components.evaluatePassword

import kotlin.math.ln
import kotlin.math.log2




fun EvaluatePassword3(password: String): Float {

    val length = password.length.toDouble()

    // Base score based on length (logarithmic)
    var score = 4 * log2(length + 1) // Logarithmic dependency on length

    // Сложность символов (с весами)
    val charTypeScores = password.map { char ->

        when{
            char.isDigit() -> 1.2
            char.isLetter() -> (if(char.isUpperCase()) 1.1 else 1.0)
            else -> 1.5
        }

    }.sum()


    score += charTypeScores * length / 10

    // Штрафы за повторы и последовательности (улучшенная логика)
    val repeatPenalty = password.groupingBy { it }.eachCount().filterValues { it > 1 }.values.sum().toDouble() * 2
    var seqPenalty = 0.0
    for (i in 0 until password.length - 2) {
        val seq = password.substring(i, i + 3)
        if (isSequential(seq)) seqPenalty += 5
    }
    score -= repeatPenalty + seqPenalty

    // Проверка на распространенные шаблоны (с использованием HashSet для скорости)
    val commonPatterns = loadCommonPatterns() // Загрузка из файла или базы данных
    if (commonPatterns.any { password.contains(it, ignoreCase = true) }) {
        score -= 20
    }

    // Проверка на слова из словаря (простейшая)
    val dictionary = loadDictionary() // Загрузка из файла или базы данных
    if (dictionary.contains(password.lowercase())) {
        score -= 30
    }

    // Оценка энтропии (с учетом частоты символов)
    val entropy = calculateEntropy3(password)
    val res = kotlin.math.max(score.toFloat(), entropy) // Берем максимум из оценки и энтропии

    return res.coerceAtLeast(0.0f) // Ограничение снизу

}

fun loadCommonPatterns(): Set<String> = loadPatternsFromFile("common_patterns.txt") // Пример
fun loadDictionary(): Set<String> = loadWordsFromFile("dictionary.txt") // Пример

fun loadPatternsFromFile(filename: String): Set<String> =  setOf("12345","qwerty") // Заглушка для примера, нужно заменить на реальную загрузку
fun loadWordsFromFile(filename: String): Set<String> = setOf("password", "qwerty") // Заглушка для примера, нужно заменить на реальную загрузку

fun isSequential(seq: String): Boolean = seq.toCharArray().sorted().joinToString("") == seq


// Метод для вычисления энтропии пароля
fun calculateEntropy3(password: String): Float {
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