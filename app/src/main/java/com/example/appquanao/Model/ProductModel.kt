package com.example.appquanao.Model


import com.google.gson.annotations.SerializedName;
import java.io.Serializable

data class ProductModel(
    @SerializedName("retailer")
    val retailer: String? = null,
    @SerializedName("img_url")
    val img_url: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("id")
    val id: String? = null
) :Serializable