package com.example.appquanao.Model

import java.io.Serializable

data class LichSuDonHangModel(
    val nguoinhan: User ?=null,
    val sanpham : List<GioHangModel>? = null
): Serializable
