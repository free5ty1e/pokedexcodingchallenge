package com.example.kotlin.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun PokemonSprite(
    spriteTitle: String,
    spriteUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(1.dp, Color.Blue, RoundedCornerShape(4.dp))
            .width(100.dp)
    ) {
        Text(
            text = spriteTitle,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
        )
        SubcomposeAsyncImage(   //Subcompose has convenient direct composable slots for loading and error but is less performant
            model = spriteUrl,
            contentDescription = contentDescription,
            modifier = modifier
                .size(100.dp)
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }

                is AsyncImagePainter.State.Error -> {
                    Text(text = "Error")
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }
}
