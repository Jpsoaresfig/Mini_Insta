package data.insta.api
import data.insta.model.AuthResponse
import data.insta.model.UserRequest
import data.insta.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/auth/login")
    suspend fun login(
        @Body request: UserRequest
    ): Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun register(
        @Body request: UserRequest
    ): Response<AuthResponse>


    @GET("/api/auth/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): Response<UserResponse>
}