package com.atik.coffeeshop.features.auth.data.models

import com.atik.coffeeshop.features.auth.domain.models.Address
import com.atik.coffeeshop.features.auth.domain.models.Gender
import com.atik.coffeeshop.features.auth.domain.models.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String?,
    @SerialName("email") val email: String?,
    @SerialName("password") val password: String?,
    @SerialName("image_url") val profileImageUrl: String?,
    @SerialName("phone_number") val phoneNumber: String?, // snake_case থেকে camelCase
    @SerialName("gender") val gender: String?,     // API থেকে String হিসেবে আসবে
    @SerialName("hobbies") val hobbies: List<String>?, // JSON Array হ্যান্ডেল করবে
    @SerialName("dob") val dateOfBirth: String?,       // API-তে সাধারণত dob থাকে
    @SerialName("bio") val bio: String?,
    @SerialName("address") val address: AddressDto?
)

@Serializable
data class AddressDto(
    @SerialName("street") val street: String?,
    @SerialName("city") val city: String?,
    @SerialName("zip_code") val zipCode: String?
)

fun UserDto.toDomain(): User {
    return User(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        email = this.email.orEmpty(),
        password = this.password.orEmpty(),
        profileImageUrl = this.profileImageUrl.orEmpty(),
        phoneNumber = this.phoneNumber.orEmpty(),
        gender = when (this.gender?.uppercase()) {
            "MALE" -> Gender.MALE
            "FEMALE" -> Gender.FEMALE
            "OTHER" -> Gender.OTHER
            else -> Gender.UNKNOWN
        },
        hobbies = this.hobbies ?: emptyList(),
        dateOfBirth = this.dateOfBirth.orEmpty(),
        bio = this.bio.orEmpty(),
        address = this.address?.toDomain() ?: Address("", "", "")
    )
}

fun AddressDto.toDomain(): Address {
    return Address(
        street = this.street.orEmpty(),
        city = this.city.orEmpty(),
        zipCode = this.zipCode.orEmpty()
    )
}