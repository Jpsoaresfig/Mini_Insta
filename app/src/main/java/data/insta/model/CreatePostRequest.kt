package data.insta.model



data class CreatePostRequest(
    val userEmail: String,
    val imageUrl: String,
    val caption: String
)
