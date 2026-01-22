package view

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import data.insta.model.Post

class PostViewModel : ViewModel() {

    val posts = mutableStateListOf<Post>()


    fun addPost(user: String, imageUrl: String, caption: String) {
        val id = posts.size + 1
        posts.add(Post(id, user, imageUrl, caption))
    }
}