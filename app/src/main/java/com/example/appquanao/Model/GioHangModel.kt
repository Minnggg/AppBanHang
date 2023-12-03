package com.example.appquanao.Model

import java.io.Serializable

data class GioHangModel(
    val idKhach: String? ?=null,
    val img_url: String? = null,
    val name: String? = null,
    val price: String? = null,
    val id: String? = null,
    val soluong: Int ?= null
): Serializable
