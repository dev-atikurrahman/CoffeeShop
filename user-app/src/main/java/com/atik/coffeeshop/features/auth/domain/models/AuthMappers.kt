package com.atik.coffeeshop.features.auth.domain.models

import com.atik.coffeeshop.features.auth.data.models.AddressDto
import com.atik.coffeeshop.features.auth.data.models.UserDto

fun UserDto.toDomain() = User(
    id = id,
    name = name,
    email = email,
    role = role,
    profile = UserProfile(
        profileImageUrl = profileImageUrl,
        phoneNumber = phoneNumber,
        gender = when (gender?.uppercase()) {
            "MALE" -> Gender.MALE
            "FEMALE" -> Gender.FEMALE
            "OTHER" -> Gender.OTHER
            else -> Gender.UNKNOWN
        },
        hobbies = hobbies.orEmpty(),
        dateOfBirth = dob,
        bio = bio,
        address = address?.toDomain()
    )
)

fun AddressDto.toDomain() = Address(
    street = street.orEmpty(),
    city = city.orEmpty(),
    zipCode = zipCode.orEmpty()
)