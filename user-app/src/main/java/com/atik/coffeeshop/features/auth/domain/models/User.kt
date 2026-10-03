package com.atik.coffeeshop.features.auth.domain.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String = "user",
    val profile: UserProfile = UserProfile()
)

data class UserProfile(
    val profileImageUrl: String? = null,
    val phoneNumber: String? = null,
    val gender: Gender = Gender.UNKNOWN,
    val hobbies: List<String> = emptyList(),
    val dateOfBirth: String? = null,
    val bio: String? = null,
    val address: Address? = null
)

enum class Gender {
    MALE, FEMALE, OTHER, UNKNOWN
}

data class Address(
    val street: String,
    val city: String,
    val zipCode: String
)