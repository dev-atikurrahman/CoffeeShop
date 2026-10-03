package com.atik.coffeeshop.shared.domain.use_case

class ValidateName {

    fun execute(name: String): ValidationResult {

        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name can't be blank"
            )
        }

        if (name.trim().length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "Enter a valid name"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}