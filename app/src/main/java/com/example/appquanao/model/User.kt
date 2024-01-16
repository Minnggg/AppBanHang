package com.example.appquanao.model

import java.io.Serializable


data class User(
    val address: List<String?>? = null,
    val name: String? = null,
    val phone: String? = null
): Serializable