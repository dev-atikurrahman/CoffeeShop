package com.atik.coffeeshop.features.auth.domain.models

data class AuthSession(val user: User, val token: String)