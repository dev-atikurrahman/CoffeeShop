package com.atik.coffeeshop.shared.domain.use_case

class ValidateRegistrationPassword {

    fun execute(password: String): ValidationResult {

        if (password.isBlank()) {
            return ValidationResult(
                false,
                "Password can't be blank"
            )
        }

        if (password.length < 8) {
            return ValidationResult(
                false,
                "Must be at least 8 characters"
            )
        }

        if (password.none { it.isUpperCase() }) {
            return ValidationResult(
                false,
                "Must contain at least 1 uppercase letter"
            )
        }

        if (password.none { it.isLowerCase() }) {
            return ValidationResult(
                false,
                "Must contain at least 1 lowercase letter"
            )
        }

        if (password.none { it.isDigit() }) {
            return ValidationResult(
                false,
                "Must contain at least 1 number"
            )
        }

        if (password.none { !it.isLetterOrDigit() }) {
            return ValidationResult(
                false,
                "Must contain at least 1 special character"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}