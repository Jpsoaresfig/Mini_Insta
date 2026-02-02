package com.mini_insta.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import view.PostViewModel
import data.insta.model.TokenManager
import android.util.Log




@Composable
fun MainScreen(
    userId: Int,
    email: String,
    onLogout: () -> Unit,
    onNavigateToPost: () -> Unit,
    postViewModel: PostViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        TokenManager.token?.let { token ->
            postViewModel.loadFeed(token)
            postViewModel.posts.forEach { post ->
                Log.d("POST_IMAGE_URL", post.imageUrl)
        }
    }}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mini Insta",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Criar post
        Button(
            onClick = onNavigateToPost,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar novo post")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Feed
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(postViewModel.posts.reversed()) { post ->
                PostItem(
                    post = post,
                    onLikeClick = { postId ->
                        postViewModel.toggleLike(
                            postId = postId,
                            userId = userId
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Logout
        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sair")
        }
    }
}
