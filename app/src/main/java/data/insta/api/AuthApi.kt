package data.insta.api
import data.insta.model.AuthResponse
import data.insta.model.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: UserRequest): Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun register(@Body request: UserRequest): Response<AuthResponse>
}