package com.atik.coffeeshop.features.auth.domain.models

// registration এর সময় শুধু name, email, password দিয়ে হবে, বাকি গুলো user পরে profile থেকে update করে নিতে পারবে।
data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val profileImageUrl: String,
    val phoneNumber: String,
    val gender: Gender,
    val hobbies: List<String>,
    val dateOfBirth: String,
    val bio: String,
    val address: Address,
)
enum class Gender {
    MALE, FEMALE, OTHER, UNKNOWN
}

data class Address(
    val street: String,
    val city: String,
    val zipCode: String
)