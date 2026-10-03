package com.atik.coffeeshop.shared.domain.use_case

class ValidateEmail {

    fun execute(email: String): ValidationResult {

        val trimmedEmail = email.trim()

        if (trimmedEmail.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email can't be blank"
            )
        }

        val emailRegex =
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        if (!emailRegex.matches(trimmedEmail)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Enter a valid email address"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}
