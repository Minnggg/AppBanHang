package com.example.appquanao.model

import java.io.Serializable

data class LichSuDonHangModel(
    val nguoinhan: User ?=null,
    val sanpham : List<GioHangModel>? = null
): Serializable
