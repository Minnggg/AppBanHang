package com.example.appquanao.Model

import java.io.Serializable


data class User(
    val address: List<String?>? = null,
    val name: String? = null,
    val phone: String? = null
): Serializable