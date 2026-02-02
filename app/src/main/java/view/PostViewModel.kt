package view

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.insta.api.ApiService
import data.insta.model.Post
import data.insta.model.TokenManager
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class PostViewModel : ViewModel() {

    val posts = mutableStateListOf<Post>()

    fun loadFeed(token: String) {
        viewModelScope.launch {
            val token = TokenManager.token ?: return@launch
            try {
                val response = ApiService.postApi.getFeed("Bearer $token")
                if (response.isSuccessful) {
                    posts.clear()
                    posts.addAll(response.body().orEmpty())
                } else {
                    Log.e("FEED_ERROR", response.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                Log.e("FEED_EXCEPTION", e.message ?: "Erro no feed", e)
            }
        }
    }

    fun addPost(
        context: Context,
        imageUri: Uri,
        caption: String,
        userEmail: String
    ) {
        viewModelScope.launch {
            try {
                val inputStream =
                    context.contentResolver.openInputStream(imageUri)
                        ?: throw Exception("Não foi possível abrir a imagem")

                val bytes = inputStream.readBytes()
                inputStream.close()

                val imageRequestBody =
                    bytes.toRequestBody("image/jpeg".toMediaType())

                val imagePart = MultipartBody.Part.createFormData(
                    name = "image",
                    filename = "image.jpg",
                    body = imageRequestBody
                )

                val captionBody =
                    caption.toRequestBody("text/plain".toMediaType())

                val userEmailBody =
                    userEmail.toRequestBody("text/plain".toMediaType())

                val response = ApiService.postApi.createPost(
                    token = "Bearer ${TokenManager.token}",
                    image = imagePart,
                    caption = captionBody
                )

                if (!response.isSuccessful) {
                    Log.e(
                        "POST_ERROR",
                        response.errorBody()?.string() ?: "Erro ao criar post"
                    )
                }

            } catch (e: Exception) {
                Log.e("POST_EXCEPTION", e.message ?: "Erro inesperado", e)
            }
        }
    }

    fun toggleLike(postId: Int, userId: Int) {
        viewModelScope.launch {

            val token = TokenManager.token ?: return@launch

            try {
                ApiService.postApi.toggleLike(postId, userId)
                loadFeed(token)
            } catch (e: Exception) {
                Log.e("LIKE_ERROR", e.message ?: "Erro ao curtir", e)
            }
        }
    }
}
