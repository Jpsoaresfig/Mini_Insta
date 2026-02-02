package com.mini_insta.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import data.insta.model.Post
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import android.util.Log
import androidx.compose.ui.layout.ContentScale

@Composable
fun PostItem(
    post: Post,
    onLikeClick: (Int) -> Unit
) {
    Card {
        Column {
            // USER
            Text(text = post.user ?: "")

            // IMAGE - Use a URL que vem do backend sem concatenar nada
            val urlFinal = post.imageUrl
            Log.d("DEBUG_FOTO", "Carregando URL: $urlFinal")

            Image(
                painter = rememberAsyncImagePainter(model = urlFinal),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            // CAPTION
            if (!post.caption.isNullOrBlank()) {
                Text(text = post.caption)
            }

            // LIKE
            Text(
                text = if (post.likedByMe) "❤️ ${post.likesCount}" else "🤍 ${post.likesCount}",
                modifier = Modifier.clickable { onLikeClick(post.id) }
            )
        }
    }
}
