package com.nrk.hellozelo.model

import com.google.gson.annotations.SerializedName

data class AlbumItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("user_id")
    val userId: Int
)