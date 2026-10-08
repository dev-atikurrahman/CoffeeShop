package com.atik.coffeeshop.features.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val role: String = "user",
    @SerialName("image_url") val profileImageUrl: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,
    val gender: String? = null,
    val hobbies: List<String>? = null,
    val dob: String? = null,
    val bio: String? = null,
    val address: AddressDto? = null
)

@Serializable
data class AddressDto(
    @SerialName("street") val street: String? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("zip_code") val zipCode: String? = null
)

@Serializable
data class AuthDataDto(val user: UserDto, val token: String)



