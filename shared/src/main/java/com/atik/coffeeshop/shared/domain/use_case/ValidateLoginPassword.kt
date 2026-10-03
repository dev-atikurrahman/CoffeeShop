package com.atik.coffeeshop.shared.domain.use_case

class ValidateLoginPassword {

    fun execute(password: String): ValidationResult {

        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}