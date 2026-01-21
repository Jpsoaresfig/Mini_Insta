package data.insta.repository

import data.insta.api.ApiService
import data.insta.model.UserRequest

class AuthRepository {

    private val api = ApiService.authApi

    suspend fun login(request: UserRequest) = api.login(request)
    suspend fun register(request: UserRequest) = api.register(request)
}
