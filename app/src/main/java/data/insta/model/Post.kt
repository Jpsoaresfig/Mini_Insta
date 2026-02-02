package data.insta.model

import com.google.gson.annotations.SerializedName

data class Post(
    val id: Int,
    val user: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val caption: String,
    val likesCount: Int,
    val likedByMe: Boolean
)