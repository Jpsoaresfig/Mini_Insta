package data.insta.api


import data.insta.model.CreatePostRequest
import data.insta.model.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.Part


interface PostApi {

    @GET("api/posts/feed")
    suspend fun getFeed(
        @Header("Authorization") token: String
    ): Response<List<Post>>

    @POST("api/posts/{postId}/like/{userId}")
    suspend fun toggleLike(
        @Path("postId") postId: Int,
        @Path("userId") userId: Int
    ): Response<Unit>

    @Multipart
    @POST("api/posts")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("caption") caption: RequestBody
    ): Response<Unit>
}