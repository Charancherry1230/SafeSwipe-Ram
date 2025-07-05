package com.example.safeswipe

fun isValidCardNumber(number: String): Boolean {
    val digits = number.filter { it.isDigit() }.map { it.toString().toInt() }.reversed()
    val sum = digits.mapIndexed { i, d ->
        if (i % 2 == 1) {
            val doubled = d * 2
            if (doubled > 9) doubled - 9 else doubled
        } else d
    }.sum()
    return sum % 10 == 0
}
