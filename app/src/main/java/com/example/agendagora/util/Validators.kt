package com.example.agendagora.util

import androidx.annotation.FloatRange
import java.util.regex.Pattern

object Validators {
    private val EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun isValidEmail(email: String): Boolean = EMAIL_REGEX.matcher(email).matches()

    fun isValidName(name: String): Boolean = name.trim().length >= 2

    fun isValidPassword(password: String): Boolean = password.length >= 6
}

enum class PasswordStrength {
    WEAK, MEDIUM, STRONG
}

data class PasswordStrengthResult(val level: PasswordStrength, @FloatRange(from = 0.0, to = 1.0) val percent: Float, val label: String)

fun evaluatePasswordStrength(password: String): PasswordStrengthResult {
    if (password.isEmpty()) return PasswordStrengthResult(PasswordStrength.WEAK, 0f, "vazia")
    var score = 0
    if (password.length >= 6) score += 1
    if (password.length >= 10) score += 1
    if (password.any { it.isDigit() }) score += 1
    if (password.any { it.isUpperCase() }) score += 1
    if (password.any { "!@#\$%^&*()-_=+[]{};:'\",.<>?/\\|`~".contains(it) }) score += 1

    return when {
        score <= 2 -> PasswordStrengthResult(PasswordStrength.WEAK, 0.25f, "fraca")
        score <= 4 -> PasswordStrengthResult(PasswordStrength.MEDIUM, 0.66f, "média")
        else -> PasswordStrengthResult(PasswordStrength.STRONG, 1f, "forte")
    }
}
