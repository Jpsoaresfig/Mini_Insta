package com.mini_insta.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import data.insta.model.Post

@Composable
fun PostItem(
    post: Post,
    onLikeClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column {

            Text(
                text = post.user.ifBlank { "Usuário" },
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.labelLarge
            )

            // 2. IMAGE
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

            // 3. LIKE E CAPTION AREA
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onLikeClick(post.id) }
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = if (post.likedByMe) "❤️" else "🤍",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "${post.likesCount} curtidas",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // CAPTION
                if (!post.caption.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = post.caption,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}